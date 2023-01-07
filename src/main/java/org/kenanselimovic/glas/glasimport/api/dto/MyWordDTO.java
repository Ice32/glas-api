package org.kenanselimovic.glas.glasimport.api.dto;

import org.kenanselimovic.glas.glasimport.domain.MyWord.MyWordExporter;

import static java.util.Objects.nonNull;

public record MyWordDTO(long id, String text) {
    public static class MyWordDTOExporter implements MyWordExporter {
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


        public MyWordDTO toValue() {
            return new MyWordDTO(id, text);
        }
    }
}
