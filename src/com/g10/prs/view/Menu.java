package com.g10.prs.view;

public abstract class Menu {
    protected String title;
    protected boolean canGoBack;

    public Menu(String title, boolean canGoBack) {
        this.title = title;
        this.canGoBack = canGoBack;
    }

    public abstract void draw();

    public int getChoice() {
        return 0;
    }

    public void handleChoice(int choice) {

    }
}
