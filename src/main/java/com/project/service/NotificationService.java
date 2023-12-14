package com.project.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.project.Telegram.MyBot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NotificationService {

    private final MyBot bot;
    private final RabbitTemplate rabbitTemplate;


    public void sendTransactionNotification(String message) {
        rabbitTemplate.convertAndSend("publication_queue", message);
    }

    public void sendNotification(String message) {
        bot.sendNotification(message);
    }
}