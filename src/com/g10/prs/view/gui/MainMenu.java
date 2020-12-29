package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/** Main Menu of the game */
public class MainMenu extends GuiMenu {

    /** class constructor */
    public MainMenu() {
        super("Menu principal", new Pair[] {
                new Pair<String, ActionListener>("Jouer", e -> PetRescueSaga.view.changeMenu(new ChooseLevelMenu())),
                new Pair<String, ActionListener>("Changer de nom", e -> {
                    String name = PetRescueSaga.player.getName();
                    GuiPopup.show(new ChangeNamePopup());
                    if (!name.equals(PetRescueSaga.player.getName())) {
                        getView().reload();
                    }
                }),
                new Pair<String, ActionListener>("Voir les règles", e -> PetRescueSaga.view.changeMenu(new RulesMenu())),
                new Pair<String, ActionListener>("Voir les crédits", e -> PetRescueSaga.view.changeMenu(new CreditsMenu())),
                new Pair<String, ActionListener>("Paramétrer", e -> PetRescueSaga.view.changeMenu(new SettingsMenu()))
        }, false, "background.png", "logo.png");
    }

    @Override
    protected void drawContent() {
        JPanel topPanel = new Panel();
        Label label = new Label("Bienvenue, " + PetRescueSaga.player.getName() + " !", 26, 0, 0, 20, 0);
        topPanel.add(label);
        panel.add(topPanel);
    }
}
