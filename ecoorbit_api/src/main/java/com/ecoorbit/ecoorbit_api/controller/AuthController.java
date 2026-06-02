package com.ecoorbit.ecoorbit_api.controller;

import com.ecoorbit.ecoorbit_api.DTO.request.CriarContaDTO;
import com.ecoorbit.ecoorbit_api.DTO.request.LoginRequestDTO;
import com.ecoorbit.ecoorbit_api.DTO.response.LoginResponseDTO;
import com.ecoorbit.ecoorbit_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CriarContaDTO dto){
        authService.registrar(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(
                authService.login(dto)
        );
    }


}
