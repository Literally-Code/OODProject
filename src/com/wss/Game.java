package com.wss;

import java.util.Scanner;

enum Difficulty {
    Easy, 
    Medium, 
    Hard
}

public class Game {
    // Fields
    private Difficulty gameDifficulty;
    private Scanner userInput;
    private Map map;
    private boolean running;

    public Game(Scanner userInput)
    {
        this.userInput = userInput;
    }

    public void initialize()
    {
        this.promptDifficulty();
        this.promptSize();
        this.running = true;

        while (this.running)
        {
            this.clearScreen();
            this.update();
            this.render();
            this.input();
        }
    }

    public void render()
    {
        this.map.renderTerrain();
    }

    public void update()
    {
        
    }

    public void input()
    {
        this.userInput.nextLine();
    }

    public void clearScreen()
    {
        System.out.print("\u001b[2J");
    }

    public static String difficultyToString(Difficulty difficulty)
    {
        if (difficulty == Difficulty.Easy)
        {
            return "Easy";
        }
        if (difficulty == Difficulty.Medium)
        {
            return "Medium";
        }
        if (difficulty == Difficulty.Hard)
        {
            return "Hard";
        }

        return "None";
    }

    private void promptDifficulty()
    {
        boolean inputSuccess = false;
        while (!inputSuccess)
        {
            System.out.println("Please input the difficulty for the simulation (0-Easy, 1-Medium, 2-Hard): ");
            String input = userInput.nextLine();
            
            try {
                int difficulty = Integer.parseInt(input);

                if (difficulty == 0)
                {
                    this.gameDifficulty = Difficulty.Easy;
                    inputSuccess = true;
                }
                if (difficulty == 1)
                {
                    this.gameDifficulty = Difficulty.Medium;
                    inputSuccess = true;
                }
                if (difficulty == 2)
                {
                    this.gameDifficulty = Difficulty.Hard;
                    inputSuccess = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid option!");
            }
        }
        System.out.println("Selected difficulty: " + difficultyToString(gameDifficulty));
    }

    public void promptSize()
    {
        boolean inputSuccess = false;
        while (!inputSuccess)
        {
            System.out.println("Please input the size of the map for the simulation (width height): ");
            String input = userInput.nextLine();
            
            try {
                String[] splitInput = input.split(" ");
                int width = Integer.parseInt(splitInput[0]);
                int height = Integer.parseInt(splitInput[1]);

                Size mapSize = new Size(width, height);
                this.map = new Map(mapSize, Math.random());
                this.map.genTerrain(this.gameDifficulty);
                inputSuccess = true;
            } catch (Exception e) {
                System.out.println("Invalid option!");
            }
        }
    }

    public Difficulty getDifficulty()
    {
        return this.gameDifficulty;
    }
}
