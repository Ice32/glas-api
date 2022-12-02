package org.kenanselimovic.glas.glasimport.infrastructure;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.kenanselimovic.glas.glasimport.domain.KnownWord;
import org.kenanselimovic.glas.glasimport.domain.KnownWordRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class KnownWordRepositoryImpl implements KnownWordRepository {
    @Inject
    Mutiny.SessionFactory sf;

    @Override
    public Uni<Void> save(KnownWord knownWord) {
        return sf.withTransaction(session -> session.persist(knownWord));
    }

    @Override
    public Uni<List<KnownWord>> findAll() {
        return sf.withTransaction((s, t) -> s
                .createNamedQuery("KnownWord.findAll", KnownWord.class)
                .getResultList());
    }

}
