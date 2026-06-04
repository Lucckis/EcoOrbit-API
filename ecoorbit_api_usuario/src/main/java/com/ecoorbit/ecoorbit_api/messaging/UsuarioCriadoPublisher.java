package com.ecoorbit.ecoorbit_api.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioCriadoPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${ecoorbit.rabbitmq.queue}")
    private String queueName;

    public void publicar(String email, String nome) {
        String mensagem = "Novo usuário: " + nome + " - " + email;
        rabbitTemplate.convertAndSend(queueName, mensagem);
        log.info("Mensagem publicada na fila {}: {}", queueName, mensagem);
    }
}