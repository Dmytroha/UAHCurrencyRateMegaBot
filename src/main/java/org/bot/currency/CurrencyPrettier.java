package org.bot.currency;

import org.bot.currency.dto.Currency;

public class CurrencyPrettier {

    public String convert(double rate, Currency currency, String bankName) {

        float roundedRate = Math.round(rate * 100d) / 100.f;
        return String.format("Курс в %s %s/UAH = %s", bankName, currency.name(), roundedRate);

    }

}
