package org.kenanselimovic.glas.glasimport.api.dto;

import javax.validation.constraints.NotNull;

public record CreateMyWordDTO(@NotNull String text) {
}
