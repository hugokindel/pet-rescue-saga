package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;

import javax.swing.*;
import java.awt.event.ActionListener;

/** Menu that shows the settings */
public class SettingsMenu extends GuiMenu {
    String themeName = getView().getStyle().name();

    /** class constructor */
    public SettingsMenu() {
        super("Paramètres", null, true, "background.png", "settings.png");

        themeName = getView().getStyle().name();

        this.categories = new Pair[] { new Pair<String, ActionListener>("Changer de thème : " + themeName, e -> {
            if (getView().getStyle() == GuiView.Style.Default) {
                this.categories[0].setObject1("Changer de thème : Stylized");
                getView().setStyle(GuiView.Style.Stylized);
            } else {
                this.categories[0].setObject1("Changer de thème : Default");
                getView().setStyle(GuiView.Style.Default);
            }
        })};
    }

    @Override
    protected void drawContent() {
        JPanel topPanel = new Panel();
        topPanel.add(new Label("Ici, vous pouvez modifier vos paramètres :", 26, 0, 0, 20, 0));
        panel.add(topPanel);
    }
}
