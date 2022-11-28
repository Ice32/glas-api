package org.kenanselimovic.glas.api.glasimport.dto;

import javax.validation.constraints.NotNull;

public record CreateImportDTO(@NotNull String title, @NotNull String text) {
}
