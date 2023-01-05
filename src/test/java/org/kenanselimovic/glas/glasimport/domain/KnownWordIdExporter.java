package org.kenanselimovic.glas.glasimport.domain;


import org.kenanselimovic.glas.glasimport.domain.KnownWord.KnownWordExporter;

public final class KnownWordIdExporter implements KnownWordExporter {
    private Long id;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long toValue() {
        return id;
    }
}
