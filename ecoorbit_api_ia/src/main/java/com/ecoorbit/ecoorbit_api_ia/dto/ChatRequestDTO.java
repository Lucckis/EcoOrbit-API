package com.ecoorbit.ecoorbit_api_ia.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequestDTO(
        @NotBlank String pergunta
) {}