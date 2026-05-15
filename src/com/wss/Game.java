package com.wss;

import java.util.Scanner;

import com.wss.map.MapGrid;
import com.wss.spacial.Size;

public class Game implements Runnable {
    // Fields
    private Difficulty gameDifficulty;
    private Scanner userInput;
    private MapGrid map;
    private boolean running;
    private boolean autoRun;

    public Game(Scanner userInput)
    {
        this.userInput = userInput;
    }

    public void run()
    {
        this.promptDifficulty();
        this.promptSize();
        this.promptAutoRun();
        this.running = true;

        while (this.running)
        {
            this.clearScreen();
            this.update();
            this.render();
            if (this.autoRun)
            {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    this.gameOver();
                }
            }
            else
            {
                this.input();
            }
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

    public void gameOver()
    {
        System.out.println("Game over!");
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
                this.map = new MapGrid(mapSize, Math.random());
                this.map.genTerrain(this.gameDifficulty);
                inputSuccess = true;
            } catch (Exception e) {
                System.out.println("Invalid option!");
            }
        }
    }

    public void promptAutoRun()
    {
        boolean inputSuccess = false;
        while (!inputSuccess)
        {
            System.out.println("Auto run? (y/n) ");
            String input = userInput.nextLine();
            
            try {
                if (input.equals("y"))
                {
                    this.autoRun = true;
                    inputSuccess = true;
                }
                else if (input.equals("n"))
                {
                    this.autoRun = false;
                    inputSuccess = true;
                }
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
