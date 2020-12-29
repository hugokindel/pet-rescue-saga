package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;

import javax.swing.*;
import java.awt.event.ActionListener;

/** Menu that shows the settings */
public class SettingsMenu extends GuiMenu {
    /** class constructor */
    public SettingsMenu() {
        super("Paramètres", null, true, "background.png", "settings.png");

        this.categories = new Pair[] { new Pair<String, ActionListener>("Changer de nom : " + PetRescueSaga.player.getName(), e -> {
            String name = PetRescueSaga.player.getName();
            GuiPopup.show(new ChangeNamePopup());
            if (!name.equals(PetRescueSaga.player.getName())) {
                this.categories[0].setObject1("Changer de nom : " + PetRescueSaga.player.getName());
                getView().reload();
            }
        }), new Pair<String, ActionListener>("Changer de thème : " + (getView().getStyle() == GuiView.Style.Default ? "Défaut" : "Stylisé"), e -> {
            if (getView().getStyle() == GuiView.Style.Default) {
                this.categories[1].setObject1("Changer de thème : Stylisé");
                getView().setStyle(GuiView.Style.Stylized);
            } else {
                this.categories[1].setObject1("Changer de thème : Défaut");
                getView().setStyle(GuiView.Style.Default);
            }
        }), new Pair<String, ActionListener>("Musique : " + (getView().getMusicState() ? "Activé" : "Désactivé"), e -> {
            if (getView().getMusicState()) {
                this.categories[2].setObject1("Musique : Désactivé");
            } else {
                this.categories[2].setObject1("Musique : Activé");
            }
            getView().setMusicState(!getView().getMusicState());
            getView().reload();
        })};
    }

    @Override
    protected void drawContent() {
        JPanel topPanel = new Panel();
        topPanel.add(new Label("Ici, vous pouvez modifier vos paramètres :", 26, 0, 0, 20, 0));
        panel.add(topPanel);
    }
}
