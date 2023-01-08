package org.kenanselimovic.glas.glasimport.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NamedQuery(name = "MyWord.findAll", query = "SELECT mw FROM MyWord mw")
public class MyWord {
    public static final short DEFAULT_FAMILIARITY = 1;
    public static final short KNOWN_FAMILIARITY = 5;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private short familiarity;

    @Embedded
    @NotNull
    private Word word;

    protected MyWord() {
        // for JPA
    }

    public MyWord(String word) {
        this.word = new Word(word);
        this.familiarity = DEFAULT_FAMILIARITY;
    }

    public static MyWord knownWord(String wordText) {
        final MyWord myWord = new MyWord(wordText);
        myWord.familiarity = KNOWN_FAMILIARITY;
        return myWord;
    }

    public void export(MyWordExporter exporter) {
        exporter.setId(id);
        exporter.setText(word.getText());
        exporter.setFamiliarity(familiarity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyWord myWord)) return false;
        return word.equals(myWord.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    public interface MyWordExporter {

        default void setId(Long id) {
        }

        default void setText(String text) {
        }

        default void setFamiliarity(short familiarity) {
        }
    }
}
