package com.ecoorbit.ecoorbit_api_predict.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PredictDTO(@NotNull Double lat,
                         @NotNull Double lon,
                         @NotNull LocalDate data) {
}
