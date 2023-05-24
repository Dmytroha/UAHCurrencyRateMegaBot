package org.bot.buttons;

import org.bot.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;



public class GetInfoButton {
    private final CurrencyTelegramBot bot;

    public GetInfoButton(CurrencyTelegramBot bot) {
        this.bot = bot;
    }
    public void execute(SendMessage message) {

        message.setText("Інформація ...");
    }

}

