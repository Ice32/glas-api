package org.kenanselimovic.glas.glasimport.api.dto;

import javax.validation.constraints.NotNull;

public record CreateImportDTO(@NotNull String title, @NotNull String text) {
}
