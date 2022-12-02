package org.kenanselimovic.glas.glasimport.domain;

import javax.persistence.*;

@Entity
@NamedQuery(name = "GlasImports.findAll", query = "SELECT gi FROM GlasImport gi")
public class GlasImport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT NOT NULL")
    private String text;


    protected GlasImport() {
        // for JPA
    }

    public GlasImport(String title, String text) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
