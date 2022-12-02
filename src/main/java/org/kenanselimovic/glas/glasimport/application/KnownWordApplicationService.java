package org.kenanselimovic.glas.glasimport.application;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import org.kenanselimovic.glas.glasimport.api.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.KnownWordDTO;
import org.kenanselimovic.glas.glasimport.domain.KnownWord;
import org.kenanselimovic.glas.glasimport.domain.KnownWordRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class KnownWordApplicationService {

    private final Logger logger = Logger.getLogger(KnownWordApplicationService.class);

    @Inject
    KnownWordRepository knownWordRepository;

    public Uni<Void> createKnownWord(CreateKnownWordDTO createKnownWordDTO) {
        return knownWordRepository.save(new KnownWord(createKnownWordDTO.text()));
    }

    public Uni<List<KnownWordDTO>> getKnownWords() {
        return knownWordRepository.findAll()
                .map(knownWords -> knownWords.stream().map(kw -> new KnownWordDTO(kw.getId(), kw.getWord().getText())).toList())
                .onItem().invoke(logger::debug);
    }
}
