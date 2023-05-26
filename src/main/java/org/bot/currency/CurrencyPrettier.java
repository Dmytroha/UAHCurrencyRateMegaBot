package org.bot.currency;

import org.bot.currency.dto.Currency;

public class CurrencyPrettier {

    public String convert(double rate, Currency currency) {

        float roundedRate = Math.round(rate * 100d) / 100.f;
        return String.format("Курс в НБУ %s/UAH = %s", currency.name(), roundedRate);

    }

}
