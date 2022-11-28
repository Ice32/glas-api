package org.kenanselimovic.glas.infrastructure.glasimport;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.kenanselimovic.glas.domain.glasimport.GlasImport;
import org.kenanselimovic.glas.domain.glasimport.GlasImportRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GlasImportRepositoryImpl implements GlasImportRepository {
    @Inject
    Mutiny.SessionFactory sf;

    @Override
    public Uni<Void> save(GlasImport glasImport) {
        return sf.withTransaction(session -> session.persist(glasImport));
    }

    @Override
    public Uni<List<GlasImport>> findAll() {
        return sf.withTransaction((s, t) -> s
                .createNamedQuery("GlasImports.findAll", GlasImport.class)
                .getResultList());
    }

    @Override
    public Uni<GlasImport> findById(long id) {
        return sf.withTransaction((session -> session.find(GlasImport.class, id)));
    }

}
