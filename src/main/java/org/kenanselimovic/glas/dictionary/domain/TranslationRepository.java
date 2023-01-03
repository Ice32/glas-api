package org.kenanselimovic.glas.dictionary.domain;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface TranslationRepository {
    Uni<Void> save(Translation translation);

    Uni<List<Translation>> findByPhrase(String text);
}
