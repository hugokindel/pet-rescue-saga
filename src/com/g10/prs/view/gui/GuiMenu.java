package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.view.Menu;

public abstract class GuiMenu extends Menu {
    public GuiMenu(String title, boolean canGoBack) {
        super(title, canGoBack);
    }

    @Override
    public void draw() {
        getWindow().setTitle("Pet Rescue Saga - " + title);

        drawContent();
    }

    protected void drawContent() {

    }

    @Override
    public int getChoice() {
        return 0;
    }

    @Override
    public void handleChoice(int choice) {

    }

    public Window getWindow() {
        return ((GuiView)PetRescueSaga.view).getWindow();
    }
}
