package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatesListenerImpl implements UpdatesListener {

    private final TelegramBot bot;
    private final BotContext botContext;

    public UpdatesListenerImpl(TelegramBot bot, BotContext botContext) {
        this.bot = bot;
        this.botContext = botContext;
    }

    @PostConstruct
    public void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> botContext.handleUpdate(update));
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
