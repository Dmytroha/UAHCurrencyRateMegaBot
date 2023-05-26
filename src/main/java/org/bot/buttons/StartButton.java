package org.bot.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.bot.telegram.CurrencyTelegramBot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StartButton {
    private final CurrencyTelegramBot bot;

    public StartButton(CurrencyTelegramBot bot) {
        this.bot = bot;
    }

    public void execute(SendMessage message) {
        String text = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
        if (message.getReplyMarkup() == null) {
            List<String> buttons = Arrays.asList("Отримати інфо", "Налаштування");
            attachButtons(message, buttons);
        }

        message.setText(text);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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