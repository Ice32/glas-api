package org.kenanselimovic.glas.api.glasimport;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.api.glasimport.dto.CreateImportDTO;
import org.kenanselimovic.glas.api.glasimport.dto.GlasImportDTO;
import org.kenanselimovic.glas.application.glasimport.GlasImportApplicationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/imports")
public class GlasImportResource {

    @Inject
    GlasImportApplicationService importApplicationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> createImport(CreateImportDTO createImportDTO) {
        return importApplicationService.createImport(createImportDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<GlasImportDTO>> getAll() {
        return importApplicationService.getImports();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GlasImportDTO> getById(@PathParam("id") Long id) {
        return importApplicationService.getImport(id);
    }
}