package org.kenanselimovic.glas.glasimport.api;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.glasimport.api.dto.CreateImportDTO;
import org.kenanselimovic.glas.glasimport.api.dto.GlasImportDTO;
import org.kenanselimovic.glas.glasimport.application.GlasImportApplicationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/imports")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GlasImportResource {

    @Inject
    GlasImportApplicationService importApplicationService;

    @POST
    public Uni<Void> createImport(@Valid CreateImportDTO createImportDTO) {
        return importApplicationService.createImport(createImportDTO);
    }

    @GET
    public Uni<List<GlasImportDTO>> getAll() {
        return importApplicationService.getImports();
    }

    @GET
    @Path("{id}")
    public Uni<GlasImportDTO> getById(@PathParam("id") Long id) {
        return importApplicationService.getImport(id);
    }
}