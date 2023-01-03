package org.kenanselimovic.glas.dictionary.domain;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.PostgresContainerResource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@Transactional
@QuarkusTestResource(PostgresContainerResource.class)
class TranslationRepositoryIntegrationTest {

    @Inject
    Mutiny.SessionFactory sf;

    @Inject
    TranslationRepository translationRepository;

    @BeforeEach
    void beforeEach() {
        sf
                .withTransaction(session -> session.createQuery("DELETE FROM Translation ").executeUpdate())
                .await().indefinitely();
    }


    @Test
    void save_SavesToDb() {
        final String translationText = "aTranslation";
        final String userQuery = "userQuery";
        final Translation translation = new Translation(translationText, "aWord", userQuery);
        final Long[] translationId = new Long[1];
        final Translation.TranslationExporter translationIdExporter = new Translation.TranslationExporter() {
            @Override
            public void setId(Long id) {
                translationId[0] = id;
            }
        };

        translationRepository.save(translation).await().indefinitely();

        translation.export(translationIdExporter);
        final Translation inserted = sf.withTransaction((session -> session.find(Translation.class, translationId[0]))).await().indefinitely();
        assertThat(inserted).isNotNull();
        inserted.export(new Translation.TranslationExporter() {
            @Override
            public void setTranslation(String translation) {
                assertThat(translation).isEqualTo(translationText);
            }

            @Override
            public void setPhrase(String query) {
                assertThat(query).isEqualTo(userQuery);
            }
        });

    }

    @Test
    void findByPhrase_ReturnsAllWithSamePhrase() {
        final String translationPhrase = "phrase";
        final String translationText = "text";
        final String translationSource = "source";
        final Translation translation = new Translation(translationText, translationSource, translationPhrase);
        sf.withTransaction(session -> session.persist(translation)).await().indefinitely();

        final List<Translation> translationsByPhrase = translationRepository.findByPhrase(translationPhrase).await().indefinitely();

        assertThat(translationsByPhrase).hasSize(1);
        final Translation insertedTranslation = translationsByPhrase.stream().findFirst().orElseThrow();
        insertedTranslation.export(new Translation.TranslationExporter() {
            @Override
            public void setTranslation(String t) {
                assertThat(t).isEqualTo(translationText);
            }

            @Override
            public void setSource(String s) {
                assertThat(s).isEqualTo(translationSource);
            }

            @Override
            public void setPhrase(String p) {
                assertThat(p).isEqualTo(translationPhrase);
            }
        });
    }
}