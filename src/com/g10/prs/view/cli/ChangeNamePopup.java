package com.g10.prs.view.cli;

import com.g10.prs.PetRescueSaga;

public class ChangeNamePopup extends CliPopup {
    public ChangeNamePopup() {
        super("Changer de nom", "Votre nom actuel est : " + PetRescueSaga.player.getName() + ".");
    }

    @Override
    protected void interact() {
        PetRescueSaga.player.setName(nextString("Veuillez choisir un nom"));
    }
}
