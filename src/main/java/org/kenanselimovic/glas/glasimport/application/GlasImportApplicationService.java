package org.kenanselimovic.glas.glasimport.application;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.glasimport.api.dto.CreateImportDTO;
import org.kenanselimovic.glas.glasimport.api.dto.GlasImportDTO;
import org.kenanselimovic.glas.glasimport.api.dto.GlasImportDTO.GlasImportDTOExporter;
import org.kenanselimovic.glas.glasimport.domain.GlasImport;
import org.kenanselimovic.glas.glasimport.domain.GlasImportRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GlasImportApplicationService {

    @Inject
    GlasImportRepository glasImportRepository;

    public Uni<Void> createImport(CreateImportDTO createImportDTO) {
        return glasImportRepository.save(new GlasImport(createImportDTO.title(), createImportDTO.text()));
    }

    public Uni<List<GlasImportDTO>> getImports() {
        return glasImportRepository.findAll().map(imports -> imports.stream().map(i -> {
            final GlasImportDTOExporter exporter = new GlasImportDTOExporter();
            i.export(exporter);
            return exporter.toValue();
        }).toList());
    }

    public Uni<GlasImportDTO> getImport(long id) {
        return glasImportRepository.findById(id).map(i -> {
            final GlasImportDTOExporter exporter = new GlasImportDTOExporter();
            i.export(exporter);
            return exporter.toValue();
        });
    }
}
