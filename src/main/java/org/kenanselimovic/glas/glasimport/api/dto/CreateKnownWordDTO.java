package org.kenanselimovic.glas.glasimport.api.dto;

import javax.validation.constraints.NotNull;

public record CreateKnownWordDTO(@NotNull String text) {
}
