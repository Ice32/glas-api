package org.kenanselimovic.glas.glasimport.domain;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface KnownWordRepository {
    Uni<Void> save(KnownWord knownWord);

    Uni<List<KnownWord>> findAll();
}
