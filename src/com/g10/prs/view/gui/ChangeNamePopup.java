package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;

public class ChangeNamePopup extends GuiPopup {
    public ChangeNamePopup() {
        super("Changer de nom", "Votre nom actuel est : " + PetRescueSaga.player.getName());
    }

    @Override
    protected void interact() {
        String s = nextString("Veuillez choisir un nom");

        if (s != null) {
            PetRescueSaga.player.setName(s);
        }
    }
}
