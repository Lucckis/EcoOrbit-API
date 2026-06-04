package com.ecoorbit.ecoorbit_api.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UsuarioCriadoListener {

    @RabbitListener(queues = "${ecoorbit.rabbitmq.queue}")
    public void onUsuarioCriado(String mensagem) {
        log.info("Mensagem recebida da fila: {}", mensagem);
    }
}