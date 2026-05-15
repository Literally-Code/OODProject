package com.wss.items;

public class Items {
    protected String name;
    protected boolean repeating;
    protected String sprite;

    public Items(String name, boolean repeating) {
        this.name = name;
        this.repeating = repeating;
        this.sprite = "X"; // Default value
    }

    public String getName() {
        return name;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public String getSprite()
    {
        return this.sprite;
    }
}