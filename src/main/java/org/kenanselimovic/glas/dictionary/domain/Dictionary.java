package org.kenanselimovic.glas.dictionary.domain;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface Dictionary {
    Uni<List<Translation>> getTranslation(String phrase);
}
