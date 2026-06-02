package com.ecoorbit.ecoorbit_api.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record CriarContaDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O email é obrigatório")
        String email,

        @NotBlank(message = "O email é obrigatório")
        String senha
) {
}
