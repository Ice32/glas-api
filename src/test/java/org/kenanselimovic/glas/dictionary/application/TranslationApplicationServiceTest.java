package org.kenanselimovic.glas.dictionary.application;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.dictionary.domain.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        translationApplicationService.getTranslation(query).await().indefinitely();

        verify(translationRepository).save(translation);
    }

}