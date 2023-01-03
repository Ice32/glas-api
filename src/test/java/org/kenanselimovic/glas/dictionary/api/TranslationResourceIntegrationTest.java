package org.kenanselimovic.glas.dictionary.api;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.PostgresContainerResource;
import org.kenanselimovic.glas.dictionary.domain.Translation;
import org.kenanselimovic.glas.dictionary.domain.TranslationRepository;
import org.kenanselimovic.glas.dictionary.infrastructure.DictCCWiremock;

import javax.inject.Inject;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@QuarkusTestResource(DictCCWiremock.class)
@QuarkusTestResource(PostgresContainerResource.class)
class TranslationResourceIntegrationTest {

    @Inject
    TranslationRepository translationRepository;

    @Inject
    Mutiny.SessionFactory sf;

    @BeforeEach
    void beforeEach() {
        sf
                .withTransaction(session -> session.createQuery("DELETE FROM Translation ").executeUpdate())
                .await().indefinitely();
    }


    @Test
    void getTranslations_ReturnsDataStubbedThroughWiremock() {
        final String phrase = "Wirt";
        given()
                .param("phrase", phrase)
                .when().get("/translations")
                .then()
                .statusCode(200)
                .body("phrase", equalTo(phrase))
                .body("translations[0].translation", equalTo("host [esp. in a hotel etc.]"))
                .body("translations[0].source", equalTo("Wirt {m}"))
                .body("translations[1].translation", equalTo("landlord [Br.] [innkeeper, publican]"))
                .body("translations[1].source", equalTo("Wirt {m} [Lokalbesitzer]"));
    }

    @Test
    void getTranslations_NewWord_SavesTranslationInDb() {
        final String phrase = "Wirt";
        given()
                .param("phrase", phrase)
                .when().get("/translations")
                .then()
                .statusCode(200);

        final List<Translation> savedTranslations = translationRepository.findByPhrase(phrase).await().indefinitely();

        assertThat(savedTranslations).contains(
                new Translation("host [esp. in a hotel etc.]", "Wirt {m}", "Wirt"),
                new Translation("landlord [Br.] [innkeeper, publican]", "Wirt {m} [Lokalbesitzer]", "Wirt")
        );
    }

    @Test
    void getTranslations_WordAlreadyCached_NoAdditionalRowsStored() {
        final String phrase = "Wirt";
        given().param("phrase", phrase).when().get("/translations");    // cache
        await().pollInSameThread().until(() -> translationRepository.findByPhrase(phrase).await().indefinitely().size() == 22);
        final int initialSize = translationRepository.findByPhrase(phrase).await().indefinitely().size();

        given()
                .param("phrase", phrase)
                .when().get("/translations")
                .then()
                .statusCode(200);

        final int newSize = translationRepository.findByPhrase(phrase).await().indefinitely().size();
        assertThat(newSize).isEqualTo(initialSize);
    }

}