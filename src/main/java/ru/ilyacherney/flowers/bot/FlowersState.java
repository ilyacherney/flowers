package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class FlowersState implements State {

    private final TelegramBot bot;

    public FlowersState(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void handleUpdate(Update update) {
        bot.execute(new SendMessage(update.message().chat().id(), "Вы в цветах!"));
    }

}
