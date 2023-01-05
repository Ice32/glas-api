package org.kenanselimovic.glas.dictionary.domain;

import org.kenanselimovic.glas.glasimport.domain.Word;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name = "Translation.findByPhrase", query = "SELECT t FROM Translation t WHERE t.phrase.text LIKE :phrase")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    @Embedded
    @AttributeOverride(name = "text",
            column = @Column(name = "source"))
    private Word source;

    @Embedded
    @AttributeOverride(name = "text",
            column = @Column(name = "query"))
    private Word phrase;

    public Translation(String text, String source, String phrase) {
        this.text = text;
        this.source = new Word(source);
        this.phrase = new Word(phrase);
    }

    protected Translation() {
        // for JPA
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Translation) obj;
        return Objects.equals(this.text, that.text) &&
                Objects.equals(this.source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, source);
    }

    @Override
    public String toString() {
        return "Translation[" +
                "text=" + text + ", " +
                "source=" + source.getText() + ']';
    }

    public void export(TranslationExporter exporter) {
        exporter.setId(id);
        exporter.setTranslation(text);
        exporter.setSource(source.getText());
        exporter.setPhrase(phrase.getText());
    }

    public interface TranslationExporter {
        default void setTranslation(String translation) {
        }

        default void setSource(String source) {
        }

        default void setPhrase(String phrase) {
        }

        default void setId(Long id) {
        }
    }
}