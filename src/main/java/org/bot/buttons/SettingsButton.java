package org.bot.buttons;

import org.bot.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsButton {
    private final CurrencyTelegramBot bot;

    public SettingsButton(CurrencyTelegramBot bot) {
        this.bot = bot;
    }
    public void execute(SendMessage message) {
        String text = "Налаштування";
        List<String> buttons = Arrays.asList("Кількість знаків після коми", "Банк", "Валюти", "Час оповіщень");
        attachButtons(message, buttons);
        message.setText(text);
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (messageText.equals("Налаштування")) {
                long chatId = getChatId(update);
                SendMessage message = createMessage("Меню налаштувань", chatId);
                execute(message);
                try {
                    executeSendMessage(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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

    private void executeSendMessage(SendMessage message) throws TelegramApiException {
        execute(message);
    }

}