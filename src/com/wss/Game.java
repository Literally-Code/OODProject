package com.wss;

import java.util.Scanner;

import com.wss.map.MapGrid;
import com.wss.player.AdventureBrain;
import com.wss.player.BalancedBrain;
import com.wss.player.Brain;
import com.wss.player.EnergeticPlayer;
import com.wss.player.GreedyBrain;
import com.wss.player.LightweightPlayer;
import com.wss.player.Player;
import com.wss.player.StandardPlayer;
import com.wss.player.vision.CautiousVision;
import com.wss.player.vision.FarSightVision;
import com.wss.player.vision.FocusedVision;
import com.wss.player.vision.KeenEyedVision;
import com.wss.player.vision.Vision;
import com.wss.spacial.Position;
import com.wss.spacial.Size;

public class Game implements Runnable {
    // Fields
    private Difficulty gameDifficulty;
    private Scanner userInput;
    private MapGrid map;
    private Player p1;
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
        this.promptPlayerType();
        this.promptBrainType();
        //There are a ton of errors in vision rn so don't run this yet
        //this.promptVisionType();
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

    public void promptPlayerType()
    {
        boolean inputSuccess = false;
        while(!inputSuccess)
        {
            System.out.println("Initializing Player... Please select a type:");
            System.out.println("[1] Standard\n[2] Energetic\n[3] Lightweight\n[4] Info");
            int input = userInput.nextInt();
            switch(input)
            {
                case(1):
                    inputSuccess = true;
                    p1 = new StandardPlayer();
                    break;
                case(2):
                    inputSuccess = true;
                    p1 = new EnergeticPlayer();
                    break;
                case(3):
                    inputSuccess = true;
                    p1 = new LightweightPlayer();
                    break;
                case(4):
                    System.out.println("Standard Player: Balanced stats\n" +
                                    "Energetic Player: More strength, less water and food\n"+
                                    "Lightweight Player: less strength, more water and food\n");
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        }
    }

    public void promptBrainType()
    {
        boolean inputSuccess = false;
        Brain b = null;
        while(!inputSuccess)
        {
            System.out.println("The Player needs a Brain! Select a type:");
            System.out.println("[1] Balanced\n[2] Greedy\n[3] Adventure\n[4] Info");
            int input = userInput.nextInt();

            switch(input)
            {
                case(1):
                    inputSuccess = true;
                    b = new BalancedBrain();
                    p1.setBrain(b);
                    break;
                case(2):
                    inputSuccess = true;
                    b = new GreedyBrain();
                    p1.setBrain(b);
                    break;
                case(3):
                    inputSuccess = true;
                    b = new AdventureBrain();
                    p1.setBrain(b);
                    break;
                case(4):
                    System.out.println("Balanced Brain: Prioritizes resource gain\n"
                                        + "Greedy Brain: Prioritizes gold and treasures spaces\n" +
                                        "Adventure Brain: Doesn't rest as often as others, prioritizes movement\n");
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        }
    }

    public void promptVisionType()
    {
        boolean inputSuccess = false;
        Vision v = null;
        Position p = new Position(0, 0);
        
        while(!inputSuccess)
        {
            System.out.println("Finally, the Player needs Vision! Select a type:");
            System.out.println("[1] Far-Sight\n[2] Focused\n[3] Keen-Eyed\n[4] Cautious\n[5] Info");
            int input = userInput.nextInt();

            switch(input)
            {
                case(1):
                    inputSuccess = true;
                    v = new FarSightVision(map, p);
                    p1.setVision(v);
                    break;
                case(2):
                    inputSuccess = true;
                    v = new FocusedVision(map, p);
                    p1.setVision(v);
                    break;
                case(3):
                    inputSuccess = true;
                    v = new KeenEyedVision(map, p);
                    p1.setVision(v);
                    break;
                case(4):
                    inputSuccess = true;
                    v = new CautiousVision(map, p);
                    p1.setVision(v);
                    break;
                case(5):
                    System.out.println("All Vision types can only see in front/to the sides of the player. Front = east\n" +
                                        "---------------------------------------------------------------------------------"+
                                        "Far-Sight Vision: player can see spaces in a two radius half circle\n" +
                                        "Focused Vision: player can only see spaces in a 1x3 rectangle directly in front\n" +
                                        "Keen-Eyed Vision: player can see 2 spaces ahead and 1 space both sides and diagonally\n"+
                                        "Cautious Vision: player can only see 1 space ahead and to the sides.\n");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }

        userInput.nextLine();
    }
    
    public Difficulty getDifficulty()
    {
        return this.gameDifficulty;
    }
}
