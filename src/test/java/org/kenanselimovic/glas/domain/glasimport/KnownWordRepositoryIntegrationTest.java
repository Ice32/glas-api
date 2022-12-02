package org.kenanselimovic.glas.domain.glasimport;

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
class KnownWordRepositoryIntegrationTest {

    @Inject
    Mutiny.SessionFactory sf;

    @Inject
    KnownWordRepository knownWordRepository;

    @BeforeEach
    void beforeEach() {
        sf
                .withTransaction(session -> session.createQuery("DELETE FROM KnownWord").executeUpdate())
                .await().indefinitely();
    }


    @Test
    void save_SavesToDb() {
        final KnownWord knownWord = new KnownWord("aWord");

        knownWordRepository.save(knownWord).await().indefinitely();

        final KnownWord inserted = sf.withTransaction((session -> session.find(KnownWord.class, knownWord.getId()))).await().indefinitely();
        assertThat(inserted).isNotNull();
        assertThat(inserted.getWord()).isEqualTo(knownWord.getWord());

    }

    @Test
    void save_WordNull_Throws() {
        final KnownWord knownWord = new KnownWord();

        assertThrows(PersistenceException.class, () -> knownWordRepository.save(knownWord).await().indefinitely());
    }

    @Test
    void save_DuplicateWord_Throws() {
        final String wordText = "aWord";
        sf.withTransaction(session -> session.persist(new KnownWord(wordText))).await().indefinitely();

        assertThrows(PersistenceException.class, () -> knownWordRepository.save(new KnownWord(wordText)).await().indefinitely());
    }

    @Test
    void findALl_ReturnsAll() {
        final KnownWord knownWord = new KnownWord("aWord");
        sf.withTransaction(session -> session.persist(knownWord)).await().indefinitely();

        final List<KnownWord> knownWords = knownWordRepository.findAll().await().indefinitely();

        assertThat(knownWords).hasSize(1);
        final KnownWord insertedKnownWord = knownWords.stream().findFirst().orElseThrow();
        assertThat(insertedKnownWord.getWord()).isEqualTo(knownWord.getWord());
    }
}