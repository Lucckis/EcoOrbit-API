package com.ecoorbit.ecoorbit_api_ia.controller;

import com.ecoorbit.ecoorbit_api_ia.dto.ChatRequestDTO;
import com.ecoorbit.ecoorbit_api_ia.dto.ChatResponseDTO;
import com.ecoorbit.ecoorbit_api_ia.service.IaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
@Tag(name = "Chat Ambiental", description = "Chat especializado em meio ambiente com IA")
public class IaController {

    private final IaService iaService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(
            @RequestBody @Valid ChatRequestDTO request,
            Authentication authentication) {

        String emailUsuario = authentication.getName();
        return ResponseEntity.ok(iaService.chat(request, emailUsuario));
    }

    @GetMapping("/chat/historico")
    @Operation(summary = "Retorna o histórico de conversa do usuário")
    public ResponseEntity<List<Map<String, String>>> historico(Authentication authentication) {

        String emailUsuario = authentication.getName();
        return ResponseEntity.ok(iaService.getHistorico(emailUsuario));
    }
}