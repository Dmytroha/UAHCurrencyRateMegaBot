package org.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandFactory {
    public static InlineKeyboardMarkup buttons(List<String> buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboard(
                        buttons.stream()
                                .map(buttonName -> {
                                    String[] nameAndText = buttonName.split(":");
                                    InlineKeyboardButton button = InlineKeyboardButton.builder()
                                            .text(nameAndText[1])
                                            .callbackData(nameAndText[0])
                                            .build();
                                    return Collections.singletonList(button);

                                })
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static InlineKeyboardMarkup dataButtons(List<String> buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons.stream()
                        .map(buttonName -> {
                            String[] nameAndText = buttonName.split(":");
                            InlineKeyboardButton button = InlineKeyboardButton.builder()
                                    .text(nameAndText[1])
                                    .callbackData(buttonName)
                                    .build();
                            return Collections.singletonList(button);
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }

}

