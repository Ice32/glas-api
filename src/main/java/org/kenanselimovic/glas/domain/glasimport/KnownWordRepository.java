package org.kenanselimovic.glas.domain.glasimport;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface KnownWordRepository {
    Uni<Void> save(KnownWord knownWord);

    Uni<List<KnownWord>> findAll();
}
