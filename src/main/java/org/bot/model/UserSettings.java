package org.bot.model;

import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
public class UserSettings {
    private String id;
    private String bank;
    private String[] currencies;
    private int decimals;
    private LocalTime notificationTime;

    private boolean isNotify;

    public UserSettings(String id) {
        this.id = id;
        bank = "ПриватБанк";
        currencies = new String[]{"USD"};
        decimals = 2;
        notificationTime = LocalTime.of(9, 0, 0);
        isNotify = true;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean isNotify) {
        this.isNotify = isNotify;
        if (!isNotify)
            this.notificationTime = null;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String[] getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String[] currencies) {
        this.currencies = currencies;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }
}

