package org.kenanselimovic.glas.glasimport.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NamedQuery(name = "KnownWord.findAll", query = "SELECT kw FROM KnownWord kw")
public class KnownWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @NotNull
    private Word word;

    protected KnownWord() {
        // for JPA
    }

    public KnownWord(String word) {
        this.word = new Word(word);
    }

    public void export(KnownWordExporter exporter) {
        exporter.setId(id);
        exporter.setText(word.getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KnownWord knownWord)) return false;
        return word.equals(knownWord.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    public interface KnownWordExporter {

        default void setId(Long id) {
        }

        default void setText(String text) {
        }
    }
}
