package org.bot.buttons;

import org.bot.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DecimalPlacesButton {
    private final CurrencyTelegramBot bot;

    public DecimalPlacesButton(CurrencyTelegramBot bot) {
        this.bot = bot;
    }

    public void execute(SendMessage message) {
        String text = "Оберіть кількість знаків після коми";
        List<String> buttons = Arrays.asList("2", "3", "4");
        attachButtons(message, buttons);
        message.setText(text);
    }

    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message = createMessage("Ви обрали " + callbackData + " знаків після коми", chatId);
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
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

    private void attachButtons(SendMessage message, List<String> buttons) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(buttons.stream()
                        .map(buttonName -> Arrays.asList(InlineKeyboardButton.builder()
                                .text(buttonName)
                                .callbackData(buttonName)
                                .build()))
                        .collect(Collectors.toList()))
                .build();

        message.setReplyMarkup(keyboardMarkup);
    }
}