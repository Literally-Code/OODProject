package com.wss.items;

public class Food extends Items {
    private int amount;

    public Food(int amount, boolean repeating) {
        super("Food", repeating);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}