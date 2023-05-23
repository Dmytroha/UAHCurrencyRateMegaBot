package org.bot.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import java.util.*;
import java.util.stream.Collectors;

public class SettingsButton {
    //@Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            if (messageText.equals("Налаштування")) {
                showSettingsMenu(chatId);
            }
        }
    }

    private SendMessage createMessage(String text, long chatId) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setParseMode("markdown");
        message.setChatId(chatId);
        return message;
    }


    private SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setParseMode("markdown");
        return message;
    }

    public Long getChatId(Update update) {
        if(update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
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



    private void showSettingsMenu(long chatId) {
        SendMessage message = createMessage("Меню налаштувань");
        message.setChatId(chatId);
        List<String> buttons = Arrays.asList("Кількість знаків після коми", "Банк", "Валюти", "Час оповіщень");
        attachButtons(message, buttons);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute(SendMessage message) throws TelegramApiException {
        try {
            execute(message);
        } catch (TelegramApiRequestException e) {
            // Обробка помилок, пов'язаних з відправкою запиту до Telegram API
            e.printStackTrace();
        }
    }

}