package org.kenanselimovic.glas.dictionary.application;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import org.kenanselimovic.glas.dictionary.api.dto.PhraseResponseDTO;
import org.kenanselimovic.glas.dictionary.api.dto.TranslationDTO;
import org.kenanselimovic.glas.dictionary.domain.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public final class TranslationApplicationService {

    private final Logger logger = Logger.getLogger(TranslationApplicationService.class);

    @Inject
    Dictionary dictionary;

    @Inject
    TranslationRepository translationRepository;


    public Uni<PhraseResponseDTO> getTranslation(String phrase) {
        return translationRepository.findByPhrase(phrase)
                .flatMap(cachedTranslations -> {
                    if (!cachedTranslations.isEmpty()) {
                        return Uni.createFrom().item(cachedTranslations);
                    }
                    return dictionary.getTranslation(phrase)
                            .onItem().invoke(this::cacheTranslations);
                })
                .map(translations -> new PhraseResponseDTO(phrase, translations.stream().map(t -> {
                    final TranslationDTO.TranslationDTOExporter exporter = new TranslationDTO.TranslationDTOExporter();
                    t.export(exporter);
                    return exporter.toValue();
                }).toList()))
                .onItem().invoke(logger::debug);
    }

    private void cacheTranslations(List<Translation> translations) {
        Uni
                .join()
                .all(translations.stream().map(translation -> translationRepository.save(translation)).toList())
                .andCollectFailures()
                .subscribeAsCompletionStage();
    }
}
