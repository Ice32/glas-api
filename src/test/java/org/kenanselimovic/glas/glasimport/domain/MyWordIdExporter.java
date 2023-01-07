package org.kenanselimovic.glas.glasimport.domain;


import org.kenanselimovic.glas.glasimport.domain.MyWord.MyWordExporter;

public final class MyWordIdExporter implements MyWordExporter {
    private Long id;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long toValue() {
        return id;
    }
}
