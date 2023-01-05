package org.kenanselimovic.glas.glasimport.domain;


public final class GlasImportIdExporter implements GlasImport.GlasImportExporter {
    private Long id;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long toValue() {
        return id;
    }
}
