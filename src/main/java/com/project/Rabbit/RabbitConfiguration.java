package com.project.Rabbit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue publicationQueue() {
        return new Queue("publication_queue");
    }

}