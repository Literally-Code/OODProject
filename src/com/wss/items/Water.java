package com.wss.items;

public class Water extends Items {
    private int amount;

    public Water(int amount, boolean repeating) {
        super("Water", repeating);
        this.amount = amount;
        this.sprite = 'W';
    }

    public int getAmount() {
        return amount;
    }
}