package com.wss;

import com.wss.map.MapGrid;
import com.wss.player.AdventureBrain;
import com.wss.player.BalancedBrain;
import com.wss.player.Brain;
import com.wss.player.Player;
import com.wss.player.vision.CautiousVision;
import com.wss.player.vision.FarSightVision;
import com.wss.player.vision.FocusedVision;
import com.wss.player.vision.KeenEyedVision;
import com.wss.player.vision.Vision;
import com.wss.spacial.Path;
import com.wss.spacial.Position;
import com.wss.spacial.Size;
import java.util.Scanner;

public class Game implements Runnable {

    private Scanner scnr;

    private Difficulty difficulty;

    private MapGrid map;

    private Player player;

    private Brain brain;

    private Vision vision;

    /*
     * true  = AI simulation
     * false = manual player
     */

    private boolean autoMode;

    public Game(Scanner scnr)
    {
        this.scnr = scnr;
    }

    @Override
    public void run()
    {
        promptDifficulty();

        promptMapSize();

        promptPlayerType();

        promptGameMode();

        /*
         * Manual mode uses a default
         * brain/vision setup.
         */

        if(autoMode)
        {
            promptBrainType();

            promptVisionType();
        }
        else
        {
            brain =
                new Brain();

            Position p =
                new Position(0, 0);

            vision =
                new FarSightVision(map, p);
        }

        map.genTerrain(difficulty);

        map.genItems();

        player.initialize(
            map,
            brain,
            vision
        );

        clearConsole();

        System.out.println(
            "================================="
        );

        System.out.println(
            "       SIMULATION STARTED        "
        );

        System.out.println(
            "=================================\n"
        );

        if(autoMode)
        {
            System.out.println(
                "Mode: AI Simulation\n"
            );
        }
        else
        {
            System.out.println(
                "Mode: Manual Player"
            );

            System.out.println(
                "Controls:"
            );

            System.out.println(
                "W = up"
            );

            System.out.println(
                "A = left"
            );

            System.out.println(
                "S = down"
            );

            System.out.println(
                "D = right"
            );

            System.out.println(
                "R = rest\n"
            );
        }

        int turns = 0;

        while(player.isAlive())
        {
            Path move;

            /*
             * AI movement
             */

            if(autoMode)
            {
                move =
                    player.makeMove();
            }

            /*
             * Manual movement
             */

            else
            {
                move =
                    player.makeManualMove(scnr);
            }

            System.out.println(
                "---------------------------------"
            );

            System.out.println(
                "Turn: " + (turns + 1)
            );

            if(move == null)
            {
                System.out.println(
                    "Move path: Resting"
                );
            }
            else
            {
                System.out.println(
                    "Move path: " + move
                );
            }

            System.out.println();

            map.renderTerrain(
                player.getCurrentPosition()
            );

            System.out.println();

            Position current =
                player.getCurrentPosition();

            /*
             * Escape condition:
             *
             * Reaching east edge
             * of map.
             */

            if(
                current.getCol() ==
                map.getSize().getWidth() - 1
            )
            {
                System.out.println(
                    "================================="
                );

                System.out.println(
                    "   PLAYER ESCAPED WILDERNESS!    "
                );

                System.out.println(
                    "================================="
                );

                break;
            }

            turns++;

            /*
             * Safety limit so
             * simulations cannot
             * run forever.
             */

            if(turns >= 150)
            {
                System.out.println(
                    "================================="
                );

                System.out.println(
                    "        TURN LIMIT REACHED       "
                );

                System.out.println(
                    "================================="
                );

                break;
            }

            try
            {
                Thread.sleep(750);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        /*
         * Death condition
         */

        if(!player.isAlive())
        {
            System.out.println(
                "================================="
            );

            System.out.println(
                "     PLAYER DID NOT SURVIVE      "
            );

            System.out.println(
                "================================="
            );
        }

        System.out.println(
            "\nSimulation Complete."
        );
    }

    public static void clearConsole() 
    {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                new ProcessBuilder("clear")
                    .inheritIO()
                    .start()
                    .waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    private void promptDifficulty()
    {
        System.out.println(
            "Choose difficulty:"
        );

        System.out.println(
            "1 - Easy"
        );

        System.out.println(
            "2 - Medium"
        );

        System.out.println(
            "3 - Hard"
        );

        int choice =
            scnr.nextInt();

        if(choice == 1)
        {
            difficulty =
                Difficulty.Easy;
        }
        else if(choice == 2)
        {
            difficulty =
                Difficulty.Medium;
        }
        else
        {
            difficulty =
                Difficulty.Hard;
        }
    }

    private void promptMapSize()
    {
        System.out.println(
            "Enter width and height:"
        );

        int width =
            scnr.nextInt();

        int height =
            scnr.nextInt();

        map =
            new MapGrid(
                new Size(width, height),
                Math.random(),
                2
            );
    }

    /*
     * Difficulty-scaled
     * starting stats.
     */

    private void promptPlayerType()
    {
        System.out.println(
            "Creating default player..."
        );

        if(difficulty == Difficulty.Easy)
        {
            player =
                new Player(
                    100,
                    100,
                    100
                );
        }
        else if(
            difficulty ==
            Difficulty.Medium
        )
        {
            player =
                new Player(
                    70,
                    70,
                    70
                );
        }
        else
        {
            player =
                new Player(
                    45,
                    45,
                    45
                );
        }
    }

    private void promptGameMode()
    {
        System.out.println(
            "Choose mode:"
        );

        System.out.println(
            "1 - Manual Player"
        );

        System.out.println(
            "2 - AI Simulation"
        );

        int choice =
            scnr.nextInt();

        autoMode =
            (choice == 2);
    }

    private void promptBrainType()
    {
        System.out.println(
            "Choose brain:"
        );

        System.out.println(
            "1 - Default"
        );

        System.out.println(
            "2 - Balanced"
        );

        System.out.println(
            "3 - Adventure"
        );

        int choice =
            scnr.nextInt();

        if(choice == 2)
        {
            brain =
                new BalancedBrain();
        }
        else if(choice == 3)
        {
            brain =
                new AdventureBrain();
        }
        else
        {
            brain =
                new Brain();
        }
    }

    private void promptVisionType()
    {
        System.out.println(
            "Choose vision:"
        );

        System.out.println(
            "1 - FarSight"
        );

        System.out.println(
            "2 - Focused"
        );

        System.out.println(
            "3 - Cautious"
        );

        System.out.println(
            "4 - KeenEyed"
        );

        int choice =
            scnr.nextInt();

        Position p =
            new Position(0, 0);

        if(choice == 2)
        {
            vision =
                new FocusedVision(map, p);
        }
        else if(choice == 3)
        {
            vision =
                new CautiousVision(map, p);
        }
        else if(choice == 4)
        {
            vision =
                new KeenEyedVision(map, p);
        }
        else
        {
            vision =
                new FarSightVision(map, p);
        }
    }
}