package com.ecoorbit.ecoorbit_api.service;

import com.ecoorbit.ecoorbit_api.DTO.request.AtualizarUsuarioDTO;
import com.ecoorbit.ecoorbit_api.DTO.response.UsuarioResponseDTO;
import com.ecoorbit.ecoorbit_api.controller.UsuarioController;
import com.ecoorbit.ecoorbit_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> {
                    var dto = new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getRole().name());
                    dto.add(linkTo(methodOn(UsuarioController.class).buscarPorId(u.getId())).withSelfRel());
                    dto.add(linkTo(methodOn(UsuarioController.class).deletar(u.getId())).withRel("deletar"));
                    return dto;
                })
                .toList();
    }

    @Cacheable(value = "usuarios", key = "#id")
    public UsuarioResponseDTO buscarPorId(Long id) {
        var u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var dto = new UsuarioResponseDTO(u.getId(), u.getNome(), u.getEmail(), u.getRole().name());
        dto.add(linkTo(methodOn(UsuarioController.class).buscarPorId(id)).withSelfRel());
        dto.add(linkTo(methodOn(UsuarioController.class).atualizar(id, null)).withRel("atualizar"));
        dto.add(linkTo(methodOn(UsuarioController.class).deletar(id)).withRel("deletar"));
        return dto;
    }

    @CacheEvict(value = "usuarios", key = "#id")
    public UsuarioResponseDTO atualizar(Long id, AtualizarUsuarioDTO dto) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.nome() != null) usuario.setNome(dto.nome());
        if (dto.senha() != null) usuario.setSenha(passwordEncoder.encode(dto.senha()));

        usuarioRepository.save(usuario);

        var response = new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
        response.add(linkTo(methodOn(UsuarioController.class).buscarPorId(id)).withSelfRel());
        return response;
    }

    @CacheEvict(value = "usuarios", key = "#id")
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}