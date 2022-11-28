package org.kenanselimovic.glas.application.glasimport;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.api.glasimport.dto.CreateImportDTO;
import org.kenanselimovic.glas.api.glasimport.dto.GlasImportDTO;
import org.kenanselimovic.glas.domain.glasimport.GlasImport;
import org.kenanselimovic.glas.domain.glasimport.GlasImportRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GlasImportApplicationService {

    @Inject
    GlasImportRepository glasImportRepository;

    public Uni<Void> createImport(CreateImportDTO createImportDTO) {
        return glasImportRepository.save(new GlasImport(createImportDTO.text()));
    }

    public Uni<List<GlasImportDTO>> getImports() {
        return glasImportRepository.findAll().map(imports -> imports.stream().map(i -> new GlasImportDTO(i.getId(), i.getText())).toList());
    }

    public Uni<GlasImportDTO> getImport(long id) {
        return glasImportRepository.findById(id).map(i -> new GlasImportDTO(i.getId(), i.getText()));
    }
}
