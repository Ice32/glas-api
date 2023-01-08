package org.kenanselimovic.glas.glasimport.domain;

import org.junit.jupiter.api.Test;
import org.kenanselimovic.glas.glasimport.domain.MyWord.MyWordExporter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kenanselimovic.glas.glasimport.domain.MyWord.DEFAULT_FAMILIARITY;
import static org.kenanselimovic.glas.glasimport.domain.MyWord.KNOWN_FAMILIARITY;

class MyWordTest {

    @Test
    void export_ExportsAllFields() {
        final String wordText = "aText";
        final MyWord myWord = new MyWord(wordText);
        final MyWordAllFieldsExporter exporter = new MyWordAllFieldsExporter();

        myWord.export(exporter);

        assertThat(exporter.text).isEqualTo(wordText);
        assertThat(exporter.familiarity).isEqualTo(DEFAULT_FAMILIARITY);
    }

    @Test
    void construct_KnownWord_FamiliarityExportableAs5() {
        final String wordText = "aText";

        final MyWord myWord = MyWord.knownWord(wordText);

        final MyWordAllFieldsExporter exporter = new MyWordAllFieldsExporter();
        myWord.export(exporter);
        assertThat(exporter.familiarity).isEqualTo(KNOWN_FAMILIARITY);
    }

    private static class MyWordAllFieldsExporter implements MyWordExporter {
        public Long id;
        public String text;
        public short familiarity;

        @Override
        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public void setText(String text) {
            this.text = text;
        }

        @Override
        public void setFamiliarity(short familiarity) {
            this.familiarity = familiarity;
        }
    }

}