package org.kenanselimovic.glas.glasimport.application;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.glasimport.api.dto.GlasImportDTO;
import org.kenanselimovic.glas.glasimport.domain.GlasImport;
import org.kenanselimovic.glas.glasimport.domain.GlasImportRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlasImportApplicationServiceTest {
    @Mock
    GlasImportRepository repository;

    @Mock
    GlasImport glasImport;

    @InjectMocks
    GlasImportApplicationService applicationService;

    @Test
    void getImport_ImportExists_ReturnsIt() {
        final long id = 1343141;
        final String importTitle = "a title";
        final String importText = "a text";
        doAnswer(invocation -> {
            final GlasImport.GlasImportExporter exporter = invocation.getArgument(0);
            exporter.setId(id);
            exporter.setTitle(importTitle);
            exporter.setText(importText);
            return null;
        }).when(glasImport).export(any());

        final GlasImportDTO expected = new GlasImportDTO(id, importTitle, importText);
        when(repository.findById(id)).thenReturn(Uni.createFrom().item(glasImport));

        final GlasImportDTO actual = applicationService.getImport(id).await().indefinitely();

        assertThat(actual).isEqualTo(expected);
    }

}