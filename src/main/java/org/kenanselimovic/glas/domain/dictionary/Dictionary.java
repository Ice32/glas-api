package org.kenanselimovic.glas.domain.dictionary;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface Dictionary {
    Uni<List<Translation>> getTranslation(String phrase);
}
