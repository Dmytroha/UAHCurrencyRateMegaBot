package org.bot.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SettingsButton {
    public static final String SETTINGS_COMMAND = "Налаштування";

    public SettingsButton() {
    }

    public void execute(SendMessage message) {
        String text = "Налаштування";
        List<String> buttons = Arrays.asList("Кількість знаків після коми", "Банк", "Валюти", "Час оповіщень");
        attachButtons(message, buttons);
        message.setText(text);
    }

    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callbackData) {
                case "Кількість знаків після коми":
                    SendMessage message = createMessage("Оберіть кількість знаків після коми", chatId);
                    List<String> buttons = Arrays.asList("2", "3", "4");
                    attachButtons(message, buttons);
                    break;
                case "Банк":
                    message = createMessage("Оберіть банк", chatId);
                    buttons = Arrays.asList("НБУ", "ПриватБанк", "Монобанк");
                    attachButtons(message, buttons);
                    break;
                case "Валюти":
                    message = createMessage("Оберіть валюту", chatId);
                    buttons = Arrays.asList("USD", "EUR");
                    attachButtons(message, buttons);
                    break;
                case "Час оповіщень":
                    message = createMessage("Оберіть час оповіщень", chatId);
                    buttons = IntStream.rangeClosed(9, 18)
                            .mapToObj(Integer::toString)
                            .collect(Collectors.toList());
                    buttons.add("Вимкнути повідомлення");
                    attachButtons(message, buttons);
                    break;
            }
        }
    }


    private SendMessage createMessage(String text, long chatId) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setParseMode("markdown");
        message.setChatId(String.valueOf(chatId));
        return message;
    }

    private long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return 0;
    }

    private void attachButtons(SendMessage message, List<String> buttons) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(Collections.singletonList(
                        buttons.stream()
                                .map(buttonName -> InlineKeyboardButton.builder()
                                        .text(buttonName)
                                        .callbackData(buttonName)
                                        .build())
                                .collect(Collectors.toList())
                ))
                .build();

        message.setReplyMarkup(keyboardMarkup);
    }

}