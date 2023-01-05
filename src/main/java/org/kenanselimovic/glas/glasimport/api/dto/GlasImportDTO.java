package org.kenanselimovic.glas.glasimport.api.dto;

import org.kenanselimovic.glas.glasimport.domain.GlasImport.GlasImportExporter;

import static java.util.Objects.nonNull;

public record GlasImportDTO(long id, String title, String text) {
    public static class GlasImportDTOExporter implements GlasImportExporter {
        private long id;
        private String title;
        private String text;

        @Override
        public void setId(Long id) {
            assert nonNull(id) : "Object is identifiable";
            this.id = id;
        }

        @Override
        public void setText(String text) {
            this.text = text;
        }

        @Override
        public void setTitle(String title) {
            this.title = title;
        }


        public GlasImportDTO toValue() {
            return new GlasImportDTO(id, title, text);
        }
    }
}
