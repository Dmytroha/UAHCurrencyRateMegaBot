package org.bot.currency.monobank;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Currency;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CurrencyParser {
    // Отримати дані з банку
    public Document getCurrency() throws IOException {
        String url = "https://api.monobank.ua/bank/currency";
        return Jsoup.connect(url).ignoreContentType(true).get();
    }

    // Метод запису отриманих данних в json
    public void writeCurrencyToJson() throws IOException {
        FileWriter writer = new FileWriter("currency.json");
        writer.write(getCurrency().body().text());
        writer.close();
    }

    // Отримати валюту
    public CurrencyDTO getCurrency(String currency) throws IOException {
        // Використовується для отримання коду валюти
        Currency curr = Currency.getInstance(currency);
        CurrencyDTO val = null;

        // Якщо настав час парсити час, відновлює дані
        if (Timer.getTimer().isTimeToParse() == true) {
            writeCurrencyToJson();
            Timer.getTimer().updateParsingTime();
        }

        FileReader reader = new FileReader("currency.json");
        Type type = TypeToken.getParameterized(List.class, CurrencyDTO.class).getType();
        // Створюємо список валют з документу
        List<CurrencyDTO> items = new Gson().fromJson(reader, type);
        // Пошук нашої валюти за кодом валюти та гривні(980)
        for (CurrencyDTO item : items) {
            if (item.getCurrencyCodeA() == curr.getNumericCode() && item.getCurrencyCodeB() == 980) {
                val = item;
                break;
            }
        }
        // Повернути об'єкт валюти
        return val;
    }
}