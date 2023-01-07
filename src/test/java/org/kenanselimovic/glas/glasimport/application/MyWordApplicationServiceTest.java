package org.kenanselimovic.glas.glasimport.application;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.glasimport.api.dto.CreateMyWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.MyWordDTO;
import org.kenanselimovic.glas.glasimport.domain.MyWord;
import org.kenanselimovic.glas.glasimport.domain.MyWord.MyWordExporter;
import org.kenanselimovic.glas.glasimport.domain.MyWordRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyWordApplicationServiceTest {

    @Mock
    MyWordRepository myWordRepository;

    @Mock
    MyWord myWord;

    @InjectMocks
    MyWordApplicationService myWordApplicationService;

    @Test
    void createMyWord_CallsRepository() {
        when(myWordRepository.save(any())).thenReturn(Uni.createFrom().voidItem());
        final String wordText = "aWord";
        final CreateMyWordDTO createMyWordDTO = new CreateMyWordDTO(wordText);

        myWordApplicationService.createMyWord(createMyWordDTO).await().indefinitely();

        verify(myWordRepository).save(new MyWord(wordText));
    }

    @Test
    void getMyWords_ReturnsWhateverRepositoryReturns() {
        final long id = 31L;
        final String wordText = "aWord";
        when(myWordRepository.findAll())
                .thenReturn(Uni.createFrom().item(singletonList(myWord)));
        doAnswer(invocation -> {
            final MyWordExporter exporter = invocation.getArgument(0);
            exporter.setId(id);
            exporter.setText(wordText);
            return null;
        }).when(myWord).export(any());

        final List<MyWordDTO> actual = myWordApplicationService.getMyWords().await().indefinitely();

        assertThat(actual).containsExactly(new MyWordDTO(id, wordText));
    }
}