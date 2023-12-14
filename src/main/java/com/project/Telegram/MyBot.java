package com.project.Telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramLongPollingBot {

    private final String botToken = "6839310678:AAGVLwcSFDRg0_wWFUzUFWzBC97Hl4LWLOQ"; 
    private final String chatId = "@uvedomleniyadlyauzum"; 

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void sendNotification(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@uzumuvedimleniyabot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
