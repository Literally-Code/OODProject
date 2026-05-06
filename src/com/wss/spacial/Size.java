package com.wss.spacial;

public class Size {
    private int width, height;

    public Size(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void addSize(int width, int height)
    {
        this.width += width;
        this.height += height;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Size clone()
    {
        return new Size(this.width, this.height);
    }
}

