package org.kenanselimovic.glas.dictionary.api;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.dictionary.api.dto.PhraseResponseDTO;
import org.kenanselimovic.glas.dictionary.application.TranslationApplicationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/translations")
public class TranslationResource {

    @Inject
    TranslationApplicationService translationApplicationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PhraseResponseDTO> getTranslations(@QueryParam("phrase") String phrase) {
        return translationApplicationService.getTranslation(phrase);
    }
}