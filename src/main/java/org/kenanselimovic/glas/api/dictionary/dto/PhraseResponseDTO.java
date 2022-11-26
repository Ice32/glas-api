package org.kenanselimovic.glas.api.dictionary.dto;

import java.util.List;

public record PhraseResponseDTO(String phrase, List<TranslationDTO> translations) {
}
