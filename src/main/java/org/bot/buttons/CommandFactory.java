package org.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;
import java.util.stream.Collectors;

public class CommandFactory {
    public static InlineKeyboardMarkup buttons(List<String> buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        buttons.stream()
                                .map(buttonName -> {
                                    String[] nameAndText = buttonName.split(":");
                                    return InlineKeyboardButton.builder()
                                            .text(nameAndText[1])
                                            .callbackData(nameAndText[0])
                                            .build();
                                })
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static InlineKeyboardMarkup dataButtons(List<String> buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons.stream()
                        .map(buttonName -> {
                            String[] nameAndText = buttonName.split(":");
                            return InlineKeyboardButton.builder()
                                    .text(nameAndText[1])
                                    .callbackData(buttonName)
                                    .build();
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

}

