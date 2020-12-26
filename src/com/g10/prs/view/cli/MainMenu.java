package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

/** Main menu of the game */
public class MainMenu extends CliMenu {

    /** class constructor */
    public MainMenu() {
        super("Menu principal", new String[] {"Jouer", "Changer de nom", "Voir les règles du jeu", "Voir les crédits"}, false);
    }

    /** draw the content before the title */
    @Override
    public void drawPrimary() {
        Out.println(TextColor.Red + " _____     _     _____                              _____                   ");
        Out.println(TextColor.Red + "|  __ \\   | |   |  __ \\                            / ____|                  ");
        Out.println(TextColor.Red + "| |__) |__| |_  | |__) |___  ___  ___ _   _  ___  | (___   __ _  __ _  __ _ ");
        Out.println(TextColor.Red + "|  ___/ _ \\ __| |  _  // _ \\/ __|/ __| | | |/ _ \\  \\___ \\ / _` |/ _` |/ _` |");
        Out.println(TextColor.Red + "| |  |  __/ |_  | | \\ \\  __/\\__ \\ (__| |_| |  __/  ____) | (_| | (_| | (_| |");
        Out.println(TextColor.Red + "|_|   \\___|\\__| |_|  \\_\\___||___/\\___|\\__,_|\\___| |_____/ \\__,_|\\__, |\\__,_|");
        Out.println(TextColor.Red + "                                                                 __/ |      ");
        Out.println(TextColor.Red + "                                                                |___/       ");
        Out.println();

        if (PetRescueSaga.isDebug()) {
            Out.println(("Informations de déboguage").toUpperCase());
            Out.println();
            Out.println("Seed : " + PetRescueSaga.getSeed());
            Out.println();
        }
    }

    /** handle the answer of the player */
    @Override
    public void handleChoice(int choice) {
        if  (choice == 1) {
            PetRescueSaga.view.changeMenu(new ChooseLevelMenu());
        } else if (choice == 2) {
            CliPopup.show(new ChangeNamePopup());
        } else if (choice == 3) {
            PetRescueSaga.view.changeMenu(new RulesMenu());
        } else if (choice == 4) {
            PetRescueSaga.view.changeMenu(new CreditsMenu());
        }
    }
}
