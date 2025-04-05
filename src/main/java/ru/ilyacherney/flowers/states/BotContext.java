package ru.ilyacherney.flowers.states;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BotContext {
    private State state;

    private final StartState startState;
    private final FlowersState flowersState;
    private final BouqetsState bouqetsState;
    private final NewBouquetState newBouquetState;
    private final AddFlowersState addFlowersState;
    private final SalesState salesState;

    public BotContext(StartState startState, FlowersState flowersState, BouqetsState bouqetsState, NewBouquetState newBouquetState, AddFlowersState addFlowersState, SalesState salesState) {
        this.state = startState;

        this.startState = startState;
        this.flowersState = flowersState;
        this.bouqetsState = bouqetsState;
        this.newBouquetState = newBouquetState;
        this.addFlowersState = addFlowersState;
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

            if (callbackQuery.data().equals("add_flowers")) {
                setState(addFlowersState);
                addFlowersState.render(chatId, editingMessageId);
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
