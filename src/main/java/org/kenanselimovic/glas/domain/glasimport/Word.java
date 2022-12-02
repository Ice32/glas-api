package org.kenanselimovic.glas.domain.glasimport;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
public class Word {
    @NotNull
    @Column(nullable = false, unique = true)
    private String text;

    public Word() {
        // for JPA
    }

    public Word(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word word)) return false;
        return getText().equals(word.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }
}
