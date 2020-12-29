package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;

import javax.swing.*;

/** Menu of the end of the level */
public class EndLevelMenu extends GuiMenu {

    /** class constructor */
    public EndLevelMenu(String status) {
        super(PetRescueSaga.level.getName() + " - Vous avez " + status + " !", null, true, "background.png", "win.png");
    }

    /** show the content */
    @Override
    protected void drawContent() {
        int numberOfPlay = PetRescueSaga.level.getNumberOfPlay();

        Panel contentPanel = new Panel();
        contentPanel.add(new Label("Vous avez joué " + numberOfPlay + " " + (numberOfPlay > 1 ? "coups" : "coup") +
                " et votre score final est de " + PetRescueSaga.level.getScore() + " !", getView().getStyle() == GuiView.Style.Stylized ? 26 : 18));
        panel.add(contentPanel);
        contentPanel.setBorder(0, 0, 20, 0);

        Panel contentPanel2 = new Panel();
        contentPanel2.add(new Label("Merci d'avoir joué à Pet Rescue Saga !", getView().getStyle() == GuiView.Style.Stylized ? 21 : 14));
        panel.add(contentPanel2);
        contentPanel2.setBorder(0, 0, 20, 0);
    }
}
