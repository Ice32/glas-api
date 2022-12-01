package org.kenanselimovic.glas.application.glasimport;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.api.glasimport.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.api.glasimport.dto.KnownWordDTO;
import org.kenanselimovic.glas.domain.glasimport.KnownWord;
import org.kenanselimovic.glas.domain.glasimport.KnownWordRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KnownWordApplicationServiceTest {

    @Mock
    KnownWordRepository knownWordRepository;

    @InjectMocks
    KnownWordApplicationService knownWordApplicationService;

    @Test
    void createKnownWord_CallsRepository() {
        when(knownWordRepository.save(any())).thenReturn(Uni.createFrom().voidItem());
        final String wordText = "aWord";
        final CreateKnownWordDTO createKnownWordDTO = new CreateKnownWordDTO(wordText);

        knownWordApplicationService.createKnownWord(createKnownWordDTO).await().indefinitely();

        verify(knownWordRepository).save(new KnownWord(wordText));
    }

    @Test
    void getKnownWords_ReturnsWhateverRepositoryReturns() {
        final String wordText = "aWord";
        when(knownWordRepository.findAll())
                .thenReturn(Uni.createFrom().item(singletonList(new KnownWord(wordText))));

        final List<KnownWordDTO> actual = knownWordApplicationService.getKnownWords().await().indefinitely();

        assertThat(actual).containsExactly(new KnownWordDTO(wordText));
    }
}