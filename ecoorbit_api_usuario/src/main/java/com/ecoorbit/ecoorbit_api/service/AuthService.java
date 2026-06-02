package com.ecoorbit.ecoorbit_api.service;

import com.ecoorbit.ecoorbit_api.DTO.request.CriarContaDTO;
import com.ecoorbit.ecoorbit_api.DTO.request.LoginRequestDTO;
import com.ecoorbit.ecoorbit_api.DTO.response.LoginResponseDTO;
import com.ecoorbit.ecoorbit_api.entity.Usuario;
import com.ecoorbit.ecoorbit_api.enums.Role;
import com.ecoorbit.ecoorbit_api.repository.UsuarioRepository;
import com.ecoorbit.ecoorbit_api.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public void registrar(CriarContaDTO dto) {

        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(
                passwordEncoder.encode(dto.senha())
        );

        usuario.setRole(Role.ROLE_USER);
        usuario.setDataCadastro(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        boolean senhaValida =
                passwordEncoder.matches(
                        dto.senha(),
                        usuario.getSenha()
                );

        if (!senhaValida) {
            throw new RuntimeException("Senha inválida");
        }

        String token = tokenService.gerarToken(usuario);

        return new LoginResponseDTO(token);
    }
}