package com.g10.prs.view.ui;

import com.g10.prs.PetRescueSaga;

public class ChangeNamePopup extends Popup {
    public ChangeNamePopup() {
        super("Changer mon nom", "Veuillez indiquez votre nouveau nom:", ReturnType.String);
    }

    @Override
    public void handleAnswer() {
        PetRescueSaga.player.setName(getAnswer());
    }
}
