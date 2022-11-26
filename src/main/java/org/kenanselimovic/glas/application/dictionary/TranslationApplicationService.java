package org.kenanselimovic.glas.application.dictionary;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.api.dictionary.dto.PhraseResponseDTO;
import org.kenanselimovic.glas.api.dictionary.dto.TranslationDTO;
import org.kenanselimovic.glas.domain.dictionary.Dictionary;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TranslationApplicationService {

    @Inject
    Dictionary dictionary;

    public Uni<PhraseResponseDTO> getTranslation(String phrase) {
        return dictionary.getTranslation(phrase)
                .map(translations -> new PhraseResponseDTO(phrase, translations.stream().map(t -> new TranslationDTO(t.translation(), t.source())).toList()));
    }
}
