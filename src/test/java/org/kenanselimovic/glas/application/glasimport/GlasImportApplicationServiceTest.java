package org.kenanselimovic.glas.application.glasimport;

import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenanselimovic.glas.api.glasimport.dto.GlasImportDTO;
import org.kenanselimovic.glas.domain.glasimport.GlasImport;
import org.kenanselimovic.glas.domain.glasimport.GlasImportRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlasImportApplicationServiceTest {
    @Mock
    GlasImportRepository repository;

    @InjectMocks
    GlasImportApplicationService applicationService;

    @Test
    void getImport_ImportExists_ReturnsIt() {
        final long id = 1343141;
        final String importTitle = "a title";
        final String importText = "a text";
        final GlasImport glasImport = new GlasImport(importTitle, importText);
        glasImport.setId(id);
        final GlasImportDTO expected = new GlasImportDTO(id, importTitle, importText);
        when(repository.findById(id)).thenReturn(Uni.createFrom().item(glasImport));

        final GlasImportDTO actual = applicationService.getImport(id).await().indefinitely();

        assertThat(actual).isEqualTo(expected);
    }

}