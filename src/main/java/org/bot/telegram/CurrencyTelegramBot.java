package org.bot.telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.bot.buttons.StartButton;
import org.bot.buttons.GetInfoButton;
import org.bot.buttons.SettingsButton;
import org.bot.service.UserStorage;


public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private final GetInfoButton getInfoButton;
    private final SettingsButton settingsButton;

    public CurrencyTelegramBot() {
        register(new StartButton());
        UserStorage userStorage = new UserStorage();
        getInfoButton = new GetInfoButton(userStorage);
        settingsButton = new SettingsButton(userStorage);
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        /*Метод виконується, коли до бота приходить команда*/
        if (update.hasCallbackQuery()) {
            SendMessage message = new SendMessage();
            boolean process = processButtons(update, message);
            if (process) {
                try {
                    super.execute(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private boolean processButtons(Update update, SendMessage message) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        message.setChatId(String.valueOf(chatId));

        String command = update.getCallbackQuery().getData();
        if (callbackData.contains(":")) {
            // if messageText contains
            command = command.split(":")[0];
        }

        boolean process = true;
        switch (command) {
            case "info":
                getInfoButton.execute(message);
                break;
            case "settings":
                settingsButton.execute(message);
                break;
            case "settings.precision":
                settingsButton.precision(message);
                break;
            case "settings.precision.data":
                String precision = callbackData.split(":")[1];
                System.out.println(precision);
                // save precision value to user, user userService to save data
                settingsButton.precisionHandler(this, update, String.valueOf(chatId), precision);
                process = false;
                break;
            case "settings.bank":
                settingsButton.bank(message);
                break;
            case "settings.bank.data":
                String bank = callbackData.split(":")[1].trim();
                System.out.println(bank);
                // save precision value to user, user userService to save data
                settingsButton.bankHandler(this, update, String.valueOf(chatId), bank);
                process = false;
                break;
            case "settings.currency":
                settingsButton.currency(message);
                break;
            case "settings.currency.data":
                String currency = callbackData.split(":")[1].trim();
                System.out.println(currency);
                // save precision value to user, user userService to save data
                settingsButton.currencyHandler( this, update, String.valueOf(chatId), currency);
                process = false;
                break;
            case "settings.notification_time":
                settingsButton.time(message);
                break;
            case "settings.notification_time.data":
                String time = callbackData.split(":")[1];
                System.out.println(time);
                // save precision value to user, user userService to save data
                settingsButton.timeHandler(this, update, String.valueOf(chatId), time);
                process = false;
                break;
            default:
                return false;
        }
        return process;
    }


    @Override
    public String getBotUsername() {
        return Constants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }

}
