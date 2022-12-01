package org.kenanselimovic.glas.domain.glasimport;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KnownWord knownWord)) return false;
        return getWord().equals(knownWord.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord());
    }
}
