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

    public void export(GlasImportExporter exporter) {
        exporter.setId(id);
        exporter.setText(text);
        exporter.setTitle(title);
    }

    public interface GlasImportExporter {

        default void setText(String text) {
        }

        default void setTitle(String title) {
        }

        default void setId(Long id) {
        }
    }
}
