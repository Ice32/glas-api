package org.kenanselimovic.glas.dictionary.application;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import org.kenanselimovic.glas.dictionary.api.dto.PhraseResponseDTO;
import org.kenanselimovic.glas.dictionary.api.dto.TranslationDTO;
import org.kenanselimovic.glas.dictionary.domain.Dictionary;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TranslationApplicationService {

    private final Logger logger = Logger.getLogger(TranslationApplicationService.class);

    @Inject
    Dictionary dictionary;

    public Uni<PhraseResponseDTO> getTranslation(String phrase) {
        return dictionary.getTranslation(phrase)
                .map(translations -> new PhraseResponseDTO(phrase, translations.stream().map(t -> new TranslationDTO(t.translation(), t.source())).toList()))
                .onItem().invoke(logger::debug);
    }
}
