package com.ecoorbit.ecoorbit_api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${ecoorbit.rabbitmq.queue}")
    private String queueName;

    @Bean
    public Queue usuarioCriadoQueue() {
        return new Queue(queueName, true);
    }
}