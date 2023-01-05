package org.kenanselimovic.glas.glasimport.api.dto;

import org.kenanselimovic.glas.glasimport.domain.KnownWord.KnownWordExporter;

import static java.util.Objects.nonNull;

public record KnownWordDTO(long id, String text) {
    public static class KnownWordDTOExporter implements KnownWordExporter {
        private long id;
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


        public KnownWordDTO toValue() {
            return new KnownWordDTO(id, text);
        }
    }
}
