package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;

import javax.swing.*;

public class EndLevelMenu extends GuiMenu {
    public EndLevelMenu(String status) {
        super(PetRescueSaga.level.getName() + " - Vous avez " + status + " !");
    }

    @Override
    protected void drawContent() {

    }
}
