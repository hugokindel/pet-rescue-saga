package com.g10.prs.view.ui;

public class LevelPopup extends Popup {
    public LevelPopup(char c) {
        super("Destruction", "Veuillez indiquez la coordonnée " + c, ReturnType.Int);
    }
}
