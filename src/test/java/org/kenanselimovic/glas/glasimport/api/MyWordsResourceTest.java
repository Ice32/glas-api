package org.kenanselimovic.glas.glasimport.api;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kenanselimovic.PostgresContainerResource;
import org.kenanselimovic.glas.glasimport.api.dto.CreateMyWordDTO;
import org.kenanselimovic.glas.glasimport.domain.MyWord;
import org.kenanselimovic.glas.glasimport.domain.MyWord.MyWordExporter;
import org.kenanselimovic.glas.glasimport.domain.MyWordRepository;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@QuarkusTestResource(PostgresContainerResource.class)
class MyWordsResourceTest {

    @Inject
    MyWordRepository repository;

    @Inject
    Mutiny.SessionFactory sf;

    @BeforeEach
    void beforeEach() {
        sf
                .withTransaction(session -> session.createQuery("DELETE FROM MyWord").executeUpdate())
                .await().indefinitely();
    }

    @Test
    void addNew_SavesInDb() {
        final String wordText = "word";
        final CreateMyWordDTO createMyWordDTO = new CreateMyWordDTO(wordText);

        given()
                .body(createMyWordDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/my-words")
                .then()
                .statusCode(204);

        final List<MyWord> insertedMyWords = repository.findAll().await().indefinitely();
        assertThat(insertedMyWords).hasSize(1);
        final MyWord inserted = insertedMyWords.get(0);
        inserted.export(new MyWordExporter() {
            @Override
            public void setText(String text) {
                assertThat(text).isEqualTo(wordText);
            }
        });
    }

    @Test
    void addNew_MissingText_Returns400() {
        given()
                .body("{}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("/my-words")
                .then()
                .statusCode(400);
    }

    @Test
    void findAll_ReturnsSavedWords() {
        final String wordText = "word";
        repository.save(new MyWord(wordText)).await().indefinitely();

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().get("/my-words")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].text", is(wordText));
    }
}