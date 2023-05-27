package org.bot.monobank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Timer {
    // Паттерн Одинак
    private static final Timer TIMER = new Timer();

    private Timer() {
    }

    // файл для збереження інформації про час
    private static String path = "src/main/resources/TimeToParse.json";

    private LocalDateTime lastParsingTime;
    private LocalDateTime nextParsingTime;

    public static Timer getTimer() {
        return TIMER;
    }

    public LocalDateTime getNextParsingTime() {
        return nextParsingTime;
    }

    // Відновити дату та час наступного парсингу
    public void updateParsingTime() {
        // отримати та встановити теперішній час
        this.lastParsingTime = LocalDateTime.now();
        // встановити дату та час наступнрого парсингу(через 5 хвилин)
        this.nextParsingTime = lastParsingTime.plusMinutes(5);
        saveToJson(path);
    }

    // Збереження в файл інформації про час
    private void saveToJson(String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(this);

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Прочитати інформацію про час
    public static Timer loadFromJson() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Timer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Метод який каже, чи час робити новий парсинг
    public boolean isTimeToParse() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        Gson gson = new Gson();
        return LocalDateTime.now().isAfter(gson.fromJson(reader, Timer.class).getNextParsingTime());

    }
}
