package com.ecoorbit.ecoorbit_api_ia.service;

import com.ecoorbit.ecoorbit_api_ia.dto.ChatRequestDTO;
import com.ecoorbit.ecoorbit_api_ia.dto.ChatResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class IaService {

    private final ChatClient chatClient;
    private final RagService ragService;
    private final Map<String, List<Message>> historicos = new ConcurrentHashMap<>();

    public ChatResponseDTO chat(ChatRequestDTO request, String emailUsuario) {

        List<Message> mensagens = historicos.getOrDefault(emailUsuario, new ArrayList<>());
        mensagens.add(new UserMessage(request.pergunta()));

        String contexto = ragService.buscarContexto(request.pergunta());

        String resposta = chatClient.prompt()
                .system("""
                    Você é um especialista em monitoramento ambiental global.
                    Use o contexto abaixo para embasar suas respostas quando relevante.
                    Responda apenas sobre meio ambiente, queimadas, desmatamento e clima.
                    
                    CONTEXTO:
                    """ + contexto)
                .messages(mensagens)
                .call()
                .content();

        mensagens.add(new AssistantMessage(resposta));
        historicos.put(emailUsuario, mensagens);

        return new ChatResponseDTO(request.pergunta(), resposta);
    }

    public List<Map<String, String>> getHistorico(String emailUsuario) {
        List<Message> mensagens = historicos.getOrDefault(emailUsuario, new ArrayList<>());

        return mensagens.stream()
                .map(m -> Map.of(
                        "role", m instanceof UserMessage ? "user" : "assistant",
                        "conteudo", m.getText()
                ))
                .toList();
    }
}