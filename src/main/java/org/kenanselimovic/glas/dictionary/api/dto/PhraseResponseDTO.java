package org.kenanselimovic.glas.dictionary.api.dto;

import java.util.List;

public record PhraseResponseDTO(String phrase, List<TranslationDTO> translations) {
}
