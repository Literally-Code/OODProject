package com.wss.items;

public class Gold extends Items {
    private int amount;

    public Gold(int amount, boolean repeating) {
        super("Gold", repeating);
        this.amount = amount;
        this.sprite = 'G';
    }

    public int getAmount() {
        return amount;
    }
}