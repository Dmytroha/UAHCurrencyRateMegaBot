package org.bot.service;

import lombok.RequiredArgsConstructor;
import org.bot.model.UserSettings;
import org.bot.telegram.CurrencyTelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService {

//    public static void startNotificationProcess(UserStorage userStorage, CurrencyTelegramBot bot) {
//        Timer timer = new Timer();
//        timer.schedule(new NotificationTask(userStorage, bot), 10_000, 3600 * 1000);
//    }
//
//    @RequiredArgsConstructor
//    static class NotificationTask extends TimerTask {
//        private final UserStorage userStorage;
//        private final CurrencyTelegramBot bot;
//
//        public void run() {
//            List<UserSettings> users = userStorage.getUsers();
//            users.forEach(user -> {
//                if (user.isNotify() && user.getNotificationTime().getHour() == LocalDateTime.now().getHour()) {
//                    notifyUser(user);
//                }
//            });
//        }
//
//        private void notifyUser(UserSettings user) {
//            SendMessage message = new SendMessage();
//            message.setChatId(user.getId());
//            message.setText("Currencies");
//
//            try {
//                bot.execute(message);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

