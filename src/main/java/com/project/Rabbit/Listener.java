package com.project.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

import com.project.Telegram.MyBot;

@AllArgsConstructor
@Component
public class Listener {

    private final MyBot bot;

    @RabbitListener(queues = "publication_queue")
    public void handleTransactionNotification(String message) {
        bot.sendNotification(message);
    }
}