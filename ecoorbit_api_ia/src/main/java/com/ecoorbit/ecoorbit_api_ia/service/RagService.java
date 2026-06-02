package com.ecoorbit.ecoorbit_api_ia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RagService {

    private final String conhecimento;

    public RagService(
            @Value("classpath:documentos/conhecimento.txt") Resource documento) {
        try {
            this.conhecimento = documento.getContentAsString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar base de conhecimento", e);
        }
    }

    public String buscarContexto(String pergunta) {
        return conhecimento;
    }
}