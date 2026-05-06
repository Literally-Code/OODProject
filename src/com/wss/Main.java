package com.wss;

import java.util.Scanner;

import com.wss.map.MapGrid;
import com.wss.spacial.Size;

public class Main {
    public static void main(String[] args) {
        Game currentGame = new Game(new Scanner(System.in));
        currentGame.initialize();

        // Example map rendering
        MapGrid newMap = new MapGrid(new Size(20, 20), 420.69);
        newMap.genTerrain(currentGame.getDifficulty());
        System.out.println("Rendering terrain...");
        newMap.renderTerrain();
    }
}