package com.g10.prs.view.cli;

import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;

public abstract class CliPopup {
    protected final String title;
    protected final String message;

    public CliPopup(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public CliPopup(String title) {
        this(title, "");
    }

    public static void show(CliPopup popup) {
        popup.draw();
        popup.interact();
    }

    public void draw() {
        drawBorder();
        drawPrimary();
        drawTitle();
        drawContent();
        drawMessage();
    }

    protected void drawBorder() {
        Out.println("--------------------------------------------------------------------------------");
        Out.clear();
    }

    protected void drawPrimary() {

    }

    protected void drawTitle() {
        Out.println(title.toUpperCase());
        Out.println();
    }

    protected void drawContent() {

    }

    protected void drawMessage() {
        if (!message.isEmpty()) {
            Out.println(message);
            Out.println();
        }
    }

    protected String nextString(String message) {
        return In.nextString(message + " (chaîne de caractère) : ");
    }

    protected int nextInt(String message, boolean hasMin, int min, boolean hasMax, int max) {
        return In.nextInt(message + " (nombre entier) : ", hasMin, min, hasMax, max);
    }

    protected abstract void interact();
}
