package com.g10.prs;

import com.g10.prs.entity.AI;
import com.g10.prs.level.Level;
import com.g10.prs.option.Command;
import com.g10.prs.option.Option;
import com.g10.prs.option.Runnable;
import com.g10.prs.common.print.Out;
import com.g10.prs.entity.Player;
import com.g10.prs.view.View;
import com.g10.prs.view.cli.MainMenu;

import java.util.Random;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    @Option(names = {"-d", "--debug"}, description = "Enables debug mode.")
    protected static boolean debug;

    @Option(names = {"-s", "--seed"}, description = "Defines a seed for generating level blocks.", usage = "<long value>")
    protected static long seed;

    @Option(names = {"--view"}, description = "Select which type of view to show", usage = "<gui> or <cli>")
    protected static String viewType;

    public static Player player = new Player();

    public static AI bot = new AI();

    public static View view;

    public static Level level;

    public static Random randomizer;

    public int run(String[] args) {
        try {
            readArguments(args, PetRescueSaga.class);

            if (seed == 0) {
                seed = System.currentTimeMillis();
            }

            randomizer = new Random(seed);

            if (viewType != null && !viewType.equals("cli") && !viewType.equals("gui")) {
                Out.println("Unknown view !");
                return 1;
            }

            if (!showHelp && !showVersion) {
                if (viewType == null || viewType.equals("cli")) {
                    view = new View(new MainMenu());
                } else {
                    //view = new SwingView();
                }

                view.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static long getSeed() {
        return seed;
    }
}
