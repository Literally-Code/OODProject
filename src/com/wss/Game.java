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

    public Game(Scanner userInput)
    {
        this.userInput = userInput;
    }

    public void initialize()
    {
        this.promptDifficulty();
        // TODO: Begin game loop
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

    public void promptDifficulty()
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

    public Difficulty getDifficulty()
    {
        return this.gameDifficulty;
    }
}
