package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bot {

    @Value("${token}")
    String token;

    @PostConstruct
    public void start() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
                updates.forEach(update -> {
                    long chatId = update.message().chat().id();
                    SendResponse response = bot.execute(new SendMessage(chatId,  update.message().text()));
                });
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
                if (e.response() != null) {
                    e.response().errorCode();
                    e.response().description();
                } else {
                    e.printStackTrace();
                }
            });
    }
}
