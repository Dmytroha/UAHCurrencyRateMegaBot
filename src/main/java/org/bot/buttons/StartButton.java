package org.bot.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StartButton extends BotCommand {

    public StartButton() {
        super("start", "start command");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        SendMessage message = new SendMessage();
        String text = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют";
        message.setText(text);
        message.setChatId(chat.getId());
        List<String> buttons = Arrays.asList("Отримати інфо", "Налаштування");
        attachButtons(message, buttons);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("can't send message to user");
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