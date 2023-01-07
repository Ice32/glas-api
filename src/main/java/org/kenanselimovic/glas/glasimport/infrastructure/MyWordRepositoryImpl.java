package org.kenanselimovic.glas.glasimport.infrastructure;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.kenanselimovic.glas.glasimport.domain.MyWord;
import org.kenanselimovic.glas.glasimport.domain.MyWordRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MyWordRepositoryImpl implements MyWordRepository {
    @Inject
    Mutiny.SessionFactory sf;

    @Override
    public Uni<Void> save(MyWord myWord) {
        return sf.withTransaction(session -> session.persist(myWord));
    }

    @Override
    public Uni<List<MyWord>> findAll() {
        return sf.withTransaction((s, t) -> s
                .createNamedQuery("MyWord.findAll", MyWord.class)
                .getResultList());
    }

}
