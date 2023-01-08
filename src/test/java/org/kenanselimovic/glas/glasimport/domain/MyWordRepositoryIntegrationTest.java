package org.kenanselimovic.glas.glasimport.domain;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.PostgresContainerResource;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@Transactional
@QuarkusTestResource(PostgresContainerResource.class)
class MyWordRepositoryIntegrationTest {

    @Inject
    Mutiny.SessionFactory sf;

    @Inject
    MyWordRepository myWordRepository;

    @BeforeEach
    void beforeEach() {
        sf
                .withTransaction(session -> session.createQuery("DELETE FROM MyWord").executeUpdate())
                .await().indefinitely();
    }


    @Test
    void save_SavesToDb() {
        final String wordText = "aWord";
        final MyWord myWord = new MyWord(wordText);

        myWordRepository.save(myWord).await().indefinitely();

        final MyWord inserted = myWordRepository.findAll().await().indefinitely().get(0);
        assertThat(inserted).isNotNull();
        inserted.export(new MyWord.MyWordExporter() {
            @Override
            public void setText(String text) {
                assertThat(text).isEqualTo(wordText);
            }
        });

    }

    @Test
    void save_WordNull_Throws() {
        final MyWord myWord = new MyWord();

        assertThrows(PersistenceException.class, () -> myWordRepository.save(myWord).await().indefinitely());
    }

    @Test
    void save_DuplicateWord_Throws() {
        final String wordText = "aWord";
        sf.withTransaction(session -> session.persist(new MyWord(wordText))).await().indefinitely();

        assertThrows(PersistenceException.class, () -> myWordRepository.save(new MyWord(wordText)).await().indefinitely());
    }

    @Test
    void findALl_ReturnsAll() {
        final String wordText = "aWord";
        final MyWord myWord = new MyWord(wordText);
        sf.withTransaction(session -> session.persist(myWord)).await().indefinitely();

        final List<MyWord> myWords = myWordRepository.findAll().await().indefinitely();

        assertThat(myWords).hasSize(1);
        final MyWord insertedMyWord = myWords.stream().findFirst().orElseThrow();
        insertedMyWord.export(new MyWord.MyWordExporter() {
            @Override
            public void setText(String text) {
                assertThat(text).isEqualTo(wordText);
            }
        });
    }
}