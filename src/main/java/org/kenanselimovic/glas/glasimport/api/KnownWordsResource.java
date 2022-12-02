package org.kenanselimovic.glas.glasimport.api;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.glasimport.api.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.KnownWordDTO;
import org.kenanselimovic.glas.glasimport.application.KnownWordApplicationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/known-words")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KnownWordsResource {

    @Inject
    KnownWordApplicationService knownWordApplicationService;

    @POST
    public Uni<Void> createKnownWord(@Valid CreateKnownWordDTO createKnownWordDTO) {
        return knownWordApplicationService.createKnownWord(createKnownWordDTO);
    }

    @GET
    public Uni<List<KnownWordDTO>> getAll() {
        return knownWordApplicationService.getKnownWords();
    }
}