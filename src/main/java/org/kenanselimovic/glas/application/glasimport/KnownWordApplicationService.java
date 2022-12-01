package org.kenanselimovic.glas.application.glasimport;

import io.smallrye.mutiny.Uni;
import org.kenanselimovic.glas.api.glasimport.dto.CreateKnownWordDTO;
import org.kenanselimovic.glas.api.glasimport.dto.KnownWordDTO;
import org.kenanselimovic.glas.domain.glasimport.KnownWord;
import org.kenanselimovic.glas.domain.glasimport.KnownWordRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class KnownWordApplicationService {

    @Inject
    KnownWordRepository knownWordRepository;

    public Uni<Void> createKnownWord(CreateKnownWordDTO createKnownWordDTO) {
        return knownWordRepository.save(new KnownWord(createKnownWordDTO.text()));
    }

    public Uni<List<KnownWordDTO>> getKnownWords() {
        return knownWordRepository.findAll()
                .map(knownWords -> knownWords.stream().map(kw -> new KnownWordDTO(kw.getWord().getText())).toList());
    }
}
