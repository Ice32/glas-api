package org.kenanselimovic.glas.glasimport.domain;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface MyWordRepository {
    Uni<Void> save(MyWord myWord);

    Uni<List<MyWord>> findAll();
}
