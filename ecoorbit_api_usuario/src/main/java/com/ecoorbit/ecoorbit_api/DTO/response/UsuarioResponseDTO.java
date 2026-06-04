package com.ecoorbit.ecoorbit_api.DTO.response;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {
    private Long id;
    private String nome;
    private String email;
    private String role;

    public UsuarioResponseDTO(Long id, String nome, String email, String role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }

}