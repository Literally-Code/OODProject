package com.wss.items;

public class Items {
    protected String name;
    protected boolean repeating;

    public Items(String name, boolean repeating) {
        this.name = name;
        this.repeating = repeating;
    }

    public String getName() {
        return name;
    }

    public boolean isRepeating() {
        return repeating;
    }
}