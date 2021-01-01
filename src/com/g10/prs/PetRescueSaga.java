package com.g10.prs;

import com.g10.prs.common.Resources;
import com.g10.prs.entity.AI;
import com.g10.prs.level.Level;
import com.g10.prs.option.Command;
import com.g10.prs.option.Option;
import com.g10.prs.option.Runnable;
import com.g10.prs.common.print.Out;
import com.g10.prs.entity.Player;
import com.g10.prs.view.View;
import com.g10.prs.view.cli.CliView;
import com.g10.prs.view.gui.GuiView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/** the class who launch the game */
@Command(name = "prs", version = "1.0.0", description = "Un jeu pour sauver des animaux.")
public class PetRescueSaga extends Runnable {
    @Option(names = {"-d", "--debug"}, description = "Active le mode déboguage.")
    protected static boolean debug;

    @Option(names = {"-s", "--seed"}, description = "Défini une graine pour la génération des niveaux.", usage = "<long value>")
    protected static long seed;

    @Option(names = {"--view"}, description = "Défini quel vue utiliser.", usage = "<gui> ou <cli>")
    protected static String viewType;

    /** the player of the game */
    public static Player player;

    /** the bot of the game */
    public static AI bot = new AI();

    /** the view of the game */
    public static View view;

    /** the level of the game */
    public static Level level;

    /** random variable */
    public static Random randomizer;

    /** run the game */
    public int run(String[] args) {
        try {
            if (!readArguments(args, PetRescueSaga.class)) {
                return 1;
            }

            if (seed == 0) {
                seed = System.currentTimeMillis();
            }

            randomizer = new Random(seed);

            if (viewType != null && !viewType.equals("cli") && !viewType.equals("gui")) {
                Out.println("Unknown view !");
                return 1;
            }

            Resources.loadSettings();
            player = new Player();

            if (!showHelp && !showVersion) {
                if (viewType == null || viewType.equals("cli")) {
                    view = new CliView();
                    view.run();
                } else {
                    javax.swing.SwingUtilities.invokeAndWait(() -> {
                        view = new GuiView();
                        view.run();
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /** @return true if is in debug mode */
    public static boolean isDebug() {
        return debug;
    }

    /** @return the seed */
    public static long getSeed() {
        return seed;
    }
}
