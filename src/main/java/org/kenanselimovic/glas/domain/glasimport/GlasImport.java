package org.kenanselimovic.glas.domain.glasimport;

import javax.persistence.*;

@Entity
@NamedQuery(name = "GlasImports.findAll", query = "SELECT gi FROM GlasImport gi")
public class GlasImport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT NOT NULL")
    private String text;

    protected GlasImport() {
        // for JPA
    }

    public GlasImport(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
