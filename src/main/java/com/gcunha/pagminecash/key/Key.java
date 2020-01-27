package com.gcunha.pagminecash.key;

public class Key {

    private final String keyString;
    private int cash;

    public Key(String keyString, int cash) {
        this.keyString = keyString;
        this.cash = cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getKeyString() {
        return keyString;
    }

    public int getCash() {
        return cash;
    }
}
