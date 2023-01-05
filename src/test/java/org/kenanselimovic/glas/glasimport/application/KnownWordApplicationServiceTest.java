package org.kenanselimovic.glas.glasimport.application;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.glasimport.api.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.KnownWordDTO;
import org.kenanselimovic.glas.glasimport.domain.KnownWord;
import org.kenanselimovic.glas.glasimport.domain.KnownWord.KnownWordExporter;
import org.kenanselimovic.glas.glasimport.domain.KnownWordRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KnownWordApplicationServiceTest {

    @Mock
    KnownWordRepository knownWordRepository;

    @Mock
    KnownWord knownWord;

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
        final long id = 31L;
        final String wordText = "aWord";
        when(knownWordRepository.findAll())
                .thenReturn(Uni.createFrom().item(singletonList(knownWord)));
        doAnswer(invocation -> {
            final KnownWordExporter exporter = invocation.getArgument(0);
            exporter.setId(id);
            exporter.setText(wordText);
            return null;
        }).when(knownWord).export(any());

        final List<KnownWordDTO> actual = knownWordApplicationService.getKnownWords().await().indefinitely();

        assertThat(actual).containsExactly(new KnownWordDTO(id, wordText));
    }
}