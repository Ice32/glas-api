package org.kenanselimovic.glas.dictionary.infrastructure;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import org.kenanselimovic.glas.dictionary.domain.Translation;
import org.kenanselimovic.glas.dictionary.domain.TranslationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TranslationRepositoryImpl implements TranslationRepository {
    @Inject
    Mutiny.SessionFactory sf;

    @Override
    public Uni<Void> save(Translation translation) {
        return sf.withTransaction(session -> session.persist(translation));
    }

    @Override
    public Uni<List<Translation>> findByPhrase(String text) {
        return sf.withTransaction((s, t) -> s
                .createNamedQuery("Translation.findByPhrase", Translation.class)
                .setParameter("phrase", text)
                .getResultList());
    }

}
