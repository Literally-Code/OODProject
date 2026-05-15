package com.wss;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game currentGame = new Game(new Scanner(System.in));
        Thread thread = new Thread(currentGame);
        
        // Prompts difficulty, size, begins simulation loop, etc.
        thread.start();
    }
}