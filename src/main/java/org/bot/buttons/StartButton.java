package org.bot.buttons;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Arrays;


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

        InlineKeyboardMarkup buttons = CommandFactory.buttons(Arrays.asList("info:Отримати інфо", "settings:Налаштування"));

        message.setReplyMarkup(buttons);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("can't send message to user");
        }

    }
}
