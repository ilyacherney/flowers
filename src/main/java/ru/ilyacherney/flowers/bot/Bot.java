package ru.ilyacherney.flowers.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.cultivar.Cultivar;
import ru.ilyacherney.flowers.cultivar.CultivarService;

import java.util.List;

@Component
public class Bot {

    private final CultivarService cultivarService;

    @Value("${token}")
    String token;

    public Bot(CultivarService cultivarService) {
        this.cultivarService = cultivarService;
    }

    @PostConstruct
    public void start() {
        TelegramBot bot = new TelegramBot(token);
        bot.setUpdatesListener(updates -> {
                updates.forEach(update -> {
                    if (update.message() != null) {
                        long chatId = update.message().chat().id();
                        SendMessage message = new SendMessage(chatId, "Меню");
                        message.parseMode(ParseMode.HTML);
                        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(
                                new InlineKeyboardButton[]{
                                        new InlineKeyboardButton("Цветы").callbackData("flowers"),
                                });
                        message.replyMarkup(keyboard);
                        SendResponse response = bot.execute(message);
                        System.out.println(response);
                    }
                    // Обработка callback-запросов
                    if (update.callbackQuery() != null) {
                        long chatId = update.callbackQuery().message().chat().id();
                        String callbackData = update.callbackQuery().data();
                        if ("flowers".equals(callbackData)) {
                            InlineKeyboardMarkup flowersKeyboard = new InlineKeyboardMarkup();
                            List<Cultivar> cultivars = cultivarService.getAllCultivars();
                            for (Cultivar cultivar : cultivars) {
                                InlineKeyboardButton[] button = {new InlineKeyboardButton(cultivar.getName()).callbackData(cultivar.getName())};
                                flowersKeyboard.addRow(button);
                            }
                            // Редактирование сообщения (только кнопки)
                            EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup(chatId, update.callbackQuery().message().messageId())
                                    .replyMarkup(flowersKeyboard);

                            bot.execute(editMessage);
                            bot.execute(new AnswerCallbackQuery(update.callbackQuery().id())); // Подтверждение callback'а
                        }
                    }
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
