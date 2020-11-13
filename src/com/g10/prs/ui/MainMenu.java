package com.g10.prs.ui;

import com.g10.prs.core.printer.Out;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Menu principal", false, new String[] {"Jouer", "Changer de nom"});
    }

    public Integer use() {
        Out.println("   _____     _     _____                              _____                   ");
        Out.println("  |  __ \\   | |   |  __ \\                            / ____|                  ");
        Out.println("  | |__) |__| |_  | |__) |___  ___  ___ _   _  ___  | (___   __ _  __ _  __ _ ");
        Out.println("  |  ___/ _ \\ __| |  _  // _ \\/ __|/ __| | | |/ _ \\  \\___ \\ / _` |/ _` |/ _` |");
        Out.println("  | |  |  __/ |_  | | \\ \\  __/\\__ \\ (__| |_| |  __/  ____) | (_| | (_| | (_| |");
        Out.println("  |_|   \\___|\\__| |_|  \\_\\___||___/\\___|\\__,_|\\___| |_____/ \\__,_|\\__, |\\__,_|");
        Out.println("                                                                   __/ |      ");
        Out.println("                                                                  |___/       ");
        Out.println();

        int result = super.use();

        if (result == 1) {
            // TODO: jouer (choisir un niveau)
        } else if (result == 2) {
            // TODO: changer nom
            // TODO: make a popup to ask name, get string result and change accordingly
        }

        return result;
    }
}
