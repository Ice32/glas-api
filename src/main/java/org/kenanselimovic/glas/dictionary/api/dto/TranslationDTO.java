package org.kenanselimovic.glas.dictionary.api.dto;

import org.kenanselimovic.glas.dictionary.domain.Translation;

public record TranslationDTO(String translation, String source) {

    public static class TranslationDTOExporter implements Translation.TranslationExporter {
        private String translation;
        private String source;

        @Override
        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public void setTranslation(String translation) {
            this.translation = translation;
        }

        public TranslationDTO toValue() {
            return new TranslationDTO(translation, source);
        }
    }
}
