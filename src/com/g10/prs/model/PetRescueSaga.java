package com.g10.prs.model;

import com.g10.prs.model.cli.annotation.Command;
import com.g10.prs.model.cli.type.Runnable;
import com.g10.prs.model.level.Level;

import java.util.Arrays;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    /** Class constructor. */
    public PetRescueSaga() {

    }

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);

        if (!showHelp && !showVersion) {
            System.out.println("   _____     _     _____                              _____                   ");
            System.out.println("  |  __ \\   | |   |  __ \\                            / ____|                  ");
            System.out.println("  | |__) |__| |_  | |__) |___  ___  ___ _   _  ___  | (___   __ _  __ _  __ _ ");
            System.out.println("  |  ___/ _ \\ __| |  _  // _ \\/ __|/ __| | | |/ _ \\  \\___ \\ / _` |/ _` |/ _` |");
            System.out.println("  | |  |  __/ |_  | | \\ \\  __/\\__ \\ (__| |_| |  __/  ____) | (_| | (_| | (_| |");
            System.out.println("  |_|   \\___|\\__| |_|  \\_\\___||___/\\___|\\__,_|\\___| |_____/ \\__,_|\\__, |\\__,_|");
            System.out.println("                                                                   __/ |      ");
            System.out.println("                                                                  |___/       ");
            System.out.println();

            // TODO: Menu principal
            try {
                Level level = new Level("1.level");

                //System.out.println(Arrays.deepToString(level.getBoard()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return 1;
            }
        }

        return 0;
    }
}
