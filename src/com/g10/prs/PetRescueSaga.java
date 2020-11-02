package com.g10.prs;

import com.g10.prs.core.cli.annotation.Command;
import com.g10.prs.core.cli.type.Runnable;
import com.g10.prs.level.Level;
import com.g10.prs.view.ViewType;
import com.g10.prs.core.printer.Out;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    public ViewType viewType;

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);

        if (!showHelp && !showVersion) {
            Out.println("   _____     _     _____                              _____                   ");
            Out.println("  |  __ \\   | |   |  __ \\                            / ____|                  ");
            Out.println("  | |__) |__| |_  | |__) |___  ___  ___ _   _  ___  | (___   __ _  __ _  __ _ ");
            Out.println("  |  ___/ _ \\ __| |  _  // _ \\/ __|/ __| | | |/ _ \\  \\___ \\ / _` |/ _` |/ _` |");
            Out.println("  | |  |  __/ |_  | | \\ \\  __/\\__ \\ (__| |_| |  __/  ____) | (_| | (_| | (_| |");
            Out.println("  |_|   \\___|\\__| |_|  \\_\\___||___/\\___|\\__,_|\\___| |_____/ \\__,_|\\__, |\\__,_|");
            Out.println("                                                                   __/ |      ");
            Out.println("                                                                  |___/       ");
            Out.println();

            // TODO: Menu principal
            try {
                Level level = new Level("1.level");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return 1;
            }
        }

        return 0;
    }
}
