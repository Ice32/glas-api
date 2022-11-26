package org.kenanselimovic;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@QuarkusTestResource(DictCCWiremock.class)
class TranslationResourceTest {

    @Test
    void testTranslationsEndpoint() {
        final String phrase = "Wirt";
        given()
                .param("phrase", phrase)
                .when().get("/translations")
                .then()
                .statusCode(200)
                .body("phrase", equalTo(phrase))
                .body("translations[0].translation", equalTo("host [esp. in a hotel etc.]"))
                .body("translations[0].source", equalTo("Wirt {m}"));
    }

}