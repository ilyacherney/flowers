package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.ilyacherney.flowers.bouquet.BouqetsState;
import ru.ilyacherney.flowers.bouquet.NewBouquetState;
import ru.ilyacherney.flowers.flower.FlowersState;
import ru.ilyacherney.flowers.sales.SalesState;

@Component
@Data
public class BotContext {
    private State state;

    private final StartState startState;
    private final FlowersState flowersState;
    private final BouqetsState bouqetsState;
    private final NewBouquetState newBouquetState;
    private final SalesState salesState;

    public BotContext(StartState startState, FlowersState flowersState, BouqetsState bouqetsState, NewBouquetState newBouquetState, SalesState salesState) {
        this.state = startState;

        this.startState = startState;
        this.flowersState = flowersState;
        this.bouqetsState = bouqetsState;
        this.newBouquetState = newBouquetState;
        this.salesState = salesState;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void handleUpdate(Update update) {

        if (update.callbackQuery() != null) {
            CallbackQuery callbackQuery = update.callbackQuery();
            long chatId = callbackQuery.message().chat().id();
            int editingMessageId = callbackQuery.message().messageId();

            if (callbackQuery.data().equals("start")) {
                setState(startState);
                startState.render(chatId, editingMessageId);
            }

            if (callbackQuery.data().equals("flowers")) {
                setState(flowersState);
                flowersState.render(chatId, editingMessageId);
            }

            if (callbackQuery.data().equals("bouquets")) {
                setState(bouqetsState);
                bouqetsState.render(chatId, editingMessageId);
            }

            if (callbackQuery.data().equals("new_bouquet")) {
                setState(newBouquetState);
                newBouquetState.render(chatId, editingMessageId);
            }


            if (callbackQuery.data().equals("sales")) {
                setState(salesState);
                salesState.render(chatId, editingMessageId);
            }

            state.handleUpdate(update);
        }

        if (update.message() != null && update.message().text().equals("/start")) {
            setState(startState);
            startState.handleUpdate(update);
        }
    }

}
