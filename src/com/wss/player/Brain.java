package com.wss.player;

import com.wss.player.vision.Vision;
import com.wss.spacial.Path;

public class Brain {

    public Brain(){}

    //Default brain takes easiest path always
    public Path makeMove(Vision eye, int[] stats)
    {
        return eye.easiestPath();
    }
}
