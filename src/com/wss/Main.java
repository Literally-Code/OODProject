package com.wss;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game currentGame = new Game(new Scanner(System.in));
        currentGame.initialize();

        // Example map rendering
        Map newMap = new Map(new Size(20, 20), 420.69);
        newMap.genTerrain(currentGame.getDifficulty());
        System.out.println("Rendering terrain...");
        newMap.renderTerrain();
    }
}