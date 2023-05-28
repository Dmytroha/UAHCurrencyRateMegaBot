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
        message.setText(text);

        InlineKeyboardMarkup buttons = CommandFactory.buttons(
                Arrays.asList(
                        "settings.precision:Кількість знаків після коми",
                        "settings.bank:Банк",
                        "settings.currency:Валюти",
                        "settings.notification_time:Час оповіщень"
                )
        );
        message.setReplyMarkup(buttons);
    }

    public void precision(SendMessage message) {
        message.setText("Оберіть кількість знаків після коми");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.precision.data:2", "settings.precision.data:3", "settings.precision.data:4"));
        message.setReplyMarkup(buttons);

    }

    public void precisionHandler(String precision) {
        System.out.println(precision);
        // save precision to user settings
    }
    public void bank(SendMessage message) {
        message.setText("Оберіть банк");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.precision.data:  НБУ ", "settings.precision.data: Приватбанк ", "settings.precision.data:  Монобанк "));
        message.setReplyMarkup(buttons);
    }
    public void bankHandler(String bank) {
        System.out.println(bank);
    }
    public void currency(SendMessage message) {
        message.setText("Оберіть валюту");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.precision.data: USD ", "settings.precision.data:  EUR "));
        message.setReplyMarkup(buttons);
    }
    public void currencyHandler(String currency) {
        System.out.println(currency);
    }
    public void time(SendMessage message) {
        message.setText("Оберіть час оповіщень");
        InlineKeyboardMarkup buttons = CommandFactory.dataButtons(Arrays.asList("settings.time.data:  9 ", "settings.time.data: 10 ", "settings.time.data:  11 ", "settings.time.data:  12 ", "settings.time.data:  13 ", "settings.time.data:  14 ", "settings.time.data:  15 ", "settings.time.data:  16 ", "settings.time.data:  17 "));
        message.setReplyMarkup(buttons);
    }
    public void timeHandler(String time) {
        System.out.println(time);
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