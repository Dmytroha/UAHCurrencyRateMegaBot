package org.bot.currency.dto.privat;

import lombok.Data;

import java.util.List;
@Data
public class CurrencyPrivatResponse {
    private String bank;
    private String baseCurrency;
    private List<ExchangeRate> exchangeRate;
    private String baseCurrencyLit;

}