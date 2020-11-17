package com.g10.prs.view.ui;

import com.g10.prs.PetRescueSaga;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Menu principal", new String[] {"Jouer", "Changer de nom"}, false);
    }

    public void handleAnswer() {
        int answer = getAnswer();

        if (answer == 1) {
            PetRescueSaga.view.showMenu(new LevelSelectionMenu());
        } else if (answer == 2) {
            PetRescueSaga.view.showPopup(new ChangeNamePopup());
        }
    }
}