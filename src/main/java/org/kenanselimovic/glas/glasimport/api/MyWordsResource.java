package org.kenanselimovic.glas.glasimport.api;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.glasimport.api.dto.CreateMyWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.MyWordDTO;
import org.kenanselimovic.glas.glasimport.application.MyWordApplicationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/my-words")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MyWordsResource {

    @Inject
    MyWordApplicationService myWordApplicationService;

    @POST
    public Uni<Void> createMyWord(@Valid CreateMyWordDTO createMyWordDTO) {
        return myWordApplicationService.createMyWord(createMyWordDTO);
    }

    @GET
    public Uni<List<MyWordDTO>> getAll() {
        return myWordApplicationService.getMyWords();
    }
}