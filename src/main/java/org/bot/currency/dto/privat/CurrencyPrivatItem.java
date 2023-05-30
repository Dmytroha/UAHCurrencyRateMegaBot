package org.bot.currency.dto.privat;

import lombok.Data;
import org.bot.currency.dto.Currency;

import java.util.List;
@Data
public class CurrencyPrivatItem {

    private Currency ccy;
    private Currency base_ccy;
    private float buy;
    private float sale;


}