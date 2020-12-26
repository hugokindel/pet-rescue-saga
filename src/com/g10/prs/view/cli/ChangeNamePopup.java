package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;

/** Popup to change the name of the player */
public class ChangeNamePopup extends CliPopup {

    /** class constructor */
    public ChangeNamePopup() {
        super("Changer de nom", "Votre nom actuel est : " + PetRescueSaga.player.getName() + ".");
    }

    /** interact with the player */
    @Override
    protected void interact() {
        PetRescueSaga.player.setName(nextString("Veuillez choisir un nom"));
    }
}
