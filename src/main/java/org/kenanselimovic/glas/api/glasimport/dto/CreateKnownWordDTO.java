package org.kenanselimovic.glas.api.glasimport.dto;

import javax.validation.constraints.NotNull;

public record CreateKnownWordDTO(@NotNull String text) {
}
