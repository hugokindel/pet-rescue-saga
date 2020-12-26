package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;

import java.awt.event.ActionListener;

/** Main Menu of the game */
public class MainMenu extends GuiMenu {

    /** class constructor */
    public MainMenu() {
        super("Menu principal", new Pair[] {
                new Pair<String, ActionListener>("Jouer", e -> PetRescueSaga.view.changeMenu(new ChooseLevelMenu())),
                new Pair<String, ActionListener>("Changer de nom", e -> GuiPopup.show(new ChangeNamePopup())),
                new Pair<String, ActionListener>("Voir les règles", e -> PetRescueSaga.view.changeMenu(new RulesMenu())),
                new Pair<String, ActionListener>("Voir les crédits", e -> PetRescueSaga.view.changeMenu(new CreditsMenu()))
        }, false);
    }
}
