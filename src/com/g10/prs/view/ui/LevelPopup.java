package com.g10.prs.view.ui;

public class LevelPopup extends Popup{
    public LevelPopup(char c) {
        super("Destruction", "Veuillez indiquez la coordonne " + c + " :", ReturnType.Int);
    }

    @Override
    public void handleAnswer() {
        // TODO : solution temporaire , objectif : le LevelPopup devient pas abstract
        // nothing
    }
}
