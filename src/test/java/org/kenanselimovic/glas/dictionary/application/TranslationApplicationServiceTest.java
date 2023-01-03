package org.kenanselimovic.glas.dictionary.application;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.dictionary.api.dto.PhraseResponseDTO;
import org.kenanselimovic.glas.dictionary.domain.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationApplicationServiceTest {

    @Mock
    private Dictionary dictionary;

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationApplicationService translationApplicationService;

    @Test
    void getTranslations_StoresRetrievedTranslationsInRepository() {
        final String query = "aPhrase";
        final Translation translation = new Translation("text", "source", "query");
        final List<Translation> translations = List.of(translation);
        when(dictionary.getTranslation(query)).thenReturn(Uni.createFrom().item(translations));
        when(translationRepository.save(any())).thenReturn(Uni.createFrom().voidItem());
        when(translationRepository.findByPhrase(query)).thenReturn(Uni.createFrom().item(emptyList()));

        translationApplicationService.getTranslation(query).await().indefinitely();

        verify(translationRepository).save(translation);
    }

    @Test
    void getTranslations_PhraseAlreadyCached_NoCallsToDictionary() {
        final String query = "aPhrase";
        final Translation translation = new Translation("text", "source", "query");
        final List<Translation> translations = List.of(translation);
        when(translationRepository.findByPhrase(query)).thenReturn(Uni.createFrom().item(translations));

        final PhraseResponseDTO result = translationApplicationService.getTranslation(query).await().indefinitely();

        verify(dictionary, times(0)).getTranslation(query);
        assertThat(result.translations()).hasSize(translations.size());
    }

}