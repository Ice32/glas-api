package org.kenanselimovic.glas.glasimport.api;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.PostgresContainerResource;
import org.kenanselimovic.glas.glasimport.api.dto.CreateImportDTO;
import org.kenanselimovic.glas.glasimport.domain.GlasImport;
import org.kenanselimovic.glas.glasimport.domain.GlasImportRepository;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@QuarkusTestResource(PostgresContainerResource.class)
class GlasImportResourceTest {
    @Inject
    GlasImportRepository repository;

    @Test
    void createImport_ImportStored() {
        final String importTitle = "title";
        final String importText = "text";
        final CreateImportDTO createImportDTO = new CreateImportDTO(importTitle, importText);

        given()
                .body(createImportDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/imports")
                .then()
                .statusCode(204);

        final List<GlasImport> insertedImports = repository.findAll().await().indefinitely();
        assertThat(insertedImports).hasSize(1);
        final GlasImport inserted = insertedImports.get(0);
        assertThat(inserted.getTitle()).isEqualTo(importTitle);
        assertThat(inserted.getText()).isEqualTo(importText);
    }

    @Test
    void createImport_TitleMissingInRequest_Returns400() {
        given()
                .body("{\"text\": \"some text\"}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/imports")
                .then()
                .statusCode(400);
    }

    @Test
    void getImport_ImportReturned() {
        final String importText = "import text";
        final String importTittle = "import title";
        final GlasImport glasImport = new GlasImport(importTittle, importText);
        repository.save(glasImport).await().indefinitely();
        final Long id = glasImport.getId();

        given()
                .pathParams("id", id)
                .when().get("/imports/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.valueOf(id.toString())))
                .body("title", equalTo(importTittle))
                .body("text", equalTo(importText));
    }

}