package org.kenanselimovic.glas.application.glasimport;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import org.kenanselimovic.glas.api.glasimport.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.api.glasimport.dto.KnownWordDTO;
import org.kenanselimovic.glas.domain.glasimport.KnownWord;
import org.kenanselimovic.glas.domain.glasimport.KnownWordRepository;

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
