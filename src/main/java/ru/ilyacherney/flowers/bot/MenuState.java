package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class MenuState implements State{

    private final TelegramBot bot;

    public MenuState(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handleUpdate(Update update) {
        long chatId = update.message().chat().id();
        bot.execute(new SendMessage(chatId, "Добро пожаловать в бот!"));
    }
}
