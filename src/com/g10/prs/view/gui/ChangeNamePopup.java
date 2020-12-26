package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;

/** Popup to change the name of the player */
public class ChangeNamePopup extends GuiPopup {

    /** class constructor */
    public ChangeNamePopup() {
        super("Changer de nom", "Votre nom actuel est : " + PetRescueSaga.player.getName());
    }

    /** interact with the player and change his name */
    @Override
    protected void interact() {
        String s = nextString("Veuillez choisir un nom");

        if (s != null) {
            PetRescueSaga.player.setName(s);
        }
    }
}
