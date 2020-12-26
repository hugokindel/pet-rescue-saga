package com.g10.prs.view;

/** base class of every menu */
public abstract class Menu {
    /** title of the menu */
    protected String title;

    /** if we can go to the previous menu */
    protected boolean canGoBack;

    /** class constructor */
    public Menu(String title, boolean canGoBack) {
        this.title = title;
        this.canGoBack = canGoBack;
    }

    /** draw the menu */
    public abstract void draw();

    /** @return the choice of the player */
    public int getChoice() {
        return 0;
    }

    /** handle the answer of the choice */
    public void handleChoice(int choice) {

    }
}
