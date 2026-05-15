package com.wss.items;

public class Water extends Items {
    private int amount;

    public Water(int amount, boolean repeating) {
        super("Water", repeating);
        this.amount = amount;
        this.sprite = "\u001b[34mW\u001b[0m";
    }

    public int getAmount() {
        return amount;
    }
}