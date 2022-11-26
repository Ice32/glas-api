package org.kenanselimovic.glas.api.dictionary;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.api.dictionary.dto.PhraseResponseDTO;
import org.kenanselimovic.glas.application.dictionary.TranslationApplicationService;

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