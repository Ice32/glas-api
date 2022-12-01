package org.kenanselimovic.glas.api.glasimport;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.PostgresContainerResource;
import org.kenanselimovic.glas.api.glasimport.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.domain.glasimport.*;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@QuarkusTestResource(PostgresContainerResource.class)
class KnownWordsResourceTest {

    @Inject
    KnownWordRepository repository;

    @Inject
    Mutiny.SessionFactory sf;

    @BeforeEach
    void beforeEach() {
        sf
                .withTransaction(session -> session.createQuery("DELETE FROM KnownWord").executeUpdate())
                .await().indefinitely();
    }

    @Test
    void addNew_SavesInDb() {
        final String wordText = "word";
        final CreateKnownWordDTO createKnownWordDTO = new CreateKnownWordDTO(wordText);

        given()
                .body(createKnownWordDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/known-words")
                .then()
                .statusCode(204);

        final List<KnownWord> insertedKnownWords = repository.findAll().await().indefinitely();
        assertThat(insertedKnownWords).hasSize(1);
        final KnownWord inserted = insertedKnownWords.get(0);
        assertThat(inserted.getWord()).isEqualTo(new Word(wordText));
    }

    @Test
    void addNew_MissingText_Returns400() {
        given()
                .body("{}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/known-words")
                .then()
                .statusCode(400);
    }

    @Test
    void findAll_ReturnsSavedWords() {
        final String wordText = "word";
        repository.save(new KnownWord(wordText)).await().indefinitely();

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get("/known-words")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].text", is(wordText));
    }
}