package org.kenanselimovic.glas.glasimport.domain;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface GlasImportRepository {
    Uni<Void> save(GlasImport glasImport);

    Uni<List<GlasImport>> findAll();

    Uni<GlasImport> findById(long id);
}
