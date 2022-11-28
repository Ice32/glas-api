package org.kenanselimovic;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.glas.domain.glasimport.GlasImport;
import org.kenanselimovic.glas.domain.glasimport.GlasImportRepository;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class GlasImportResourceTest {
    @Inject
    GlasImportRepository repository;

    @Test
    void testGetImportEndpoint() {
        final String importText = "import text";
        final GlasImport glasImport = new GlasImport(importText);
        repository.save(glasImport).await().indefinitely();
        final Long id = glasImport.getId();

        given()
                .pathParams("id", id)
                .when().get("/imports/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.valueOf(id.toString())))
                .body("text", equalTo(importText));
    }

}