package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;
import com.g10.prs.view.ui.LevelMenu;
import com.g10.prs.view.ui.MainMenu;
import com.g10.prs.view.ui.Menu;
import com.g10.prs.view.ui.Popup;
import com.g10.prs.view.View;

public class CliView extends View {
    @Override
    public void showPopup(Popup popup) {
        Out.println("--------------------------------------------------------------------------------");
        Out.clear();
        Out.println(popup.getTitle().toUpperCase());
        Out.println();
        Out.print(popup.getDescription());

        popup.handleAnswer();
    }

    @Override
    public int nextAnswer() {
        String description = "Sélectionnez une catégorie du menu" + (currentMenu.canGoBack() ? ", 'q' pour quitter ou 'b' pour revenir au menu précédent" : " ou 'q' pour quitter") + ": ";
        return In.nextAnswer(description, currentMenu.canGoBack(), currentMenu.getCategories().length);
    }

    @Override
    public String nextString() {
        return In.nextString(" (chaîne de caractère): ");
    }

    @Override
    public int nextInt() {
        return In.nextInt(" (nombre entier): ");
    }

    @Override
    protected void drawMenu(Menu menu) {
        Out.println("--------------------------------------------------------------------------------");
        Out.clear();

        if (menu instanceof MainMenu) {
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
                Out.println("INFORMATIONS DE DÉBOGUAGE");
                Out.println();
                Out.println("Seed: " + PetRescueSaga.getSeed());
                Out.println();
            }
        }

        Out.println(menu.getTitle().toUpperCase());
        Out.println();

        if (menu instanceof LevelMenu) {
            PetRescueSaga.level.print();
            Out.println();
        }

        for (int i = 0; i < menu.getCategories().length; i++) {
            Out.println(i + 1 + ". " + menu.getCategories()[i]);
        }
        Out.println();
    }

    @Override
    protected void end() {
        Out.end();
    }
}
