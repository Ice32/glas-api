package org.kenanselimovic.glas.glasimport.application;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import org.kenanselimovic.glas.glasimport.api.dto.CreateMyWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.MyWordDTO;
import org.kenanselimovic.glas.glasimport.api.dto.MyWordDTO.MyWordDTOExporter;
import org.kenanselimovic.glas.glasimport.domain.MyWord;
import org.kenanselimovic.glas.glasimport.domain.MyWordRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MyWordApplicationService {

    private final Logger logger = Logger.getLogger(MyWordApplicationService.class);

    @Inject
    MyWordRepository myWordRepository;

    public Uni<Void> createMyWord(CreateMyWordDTO createMyWordDTO) {
        return myWordRepository.save(createMyWordDTO.isKnown()
                ? MyWord.knownWord(createMyWordDTO.text())
                : new MyWord(createMyWordDTO.text()));
    }

    public Uni<List<MyWordDTO>> getMyWords() {
        return myWordRepository.findAll()
                .map(myWords -> myWords.stream().map(mw -> {
                    final MyWordDTOExporter exporter = new MyWordDTOExporter();
                    mw.export(exporter);
                    return exporter.toValue();
                }).toList())
                .onItem().invoke(logger::debug);
    }
}
