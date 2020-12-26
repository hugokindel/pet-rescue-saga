package com.g10.prs.view.cli;

import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;

/** base class for Cli Popup */
public abstract class CliPopup {
    /** title of the popup */
    protected final String title;
    /** message of the popup */
    protected final String message;

    /** class constructor */
    public CliPopup(String title, String message) {
        this.title = title;
        this.message = message;
    }

    /** class constructor */
    public CliPopup(String title) {
        this(title, "");
    }

    /** draw the popup than interact with the player */
    public static void show(CliPopup popup) {
        popup.draw();
        popup.interact();
    }

    /** draw the popup */
    public void draw() {
        drawBorder();
        drawPrimary();
        drawTitle();
        drawContent();
        drawMessage();
    }

    /** draw the border of the popup */
    protected void drawBorder() {
        Out.println("--------------------------------------------------------------------------------");
        Out.clear();
    }

    /** draw the content before the title */
    protected void drawPrimary() {

    }

    /** draw the title */
    protected void drawTitle() {
        Out.println(title.toUpperCase());
        Out.println();
    }

    /** draw the content */
    protected void drawContent() {

    }

    /** draw the message */
    protected void drawMessage() {
        if (!message.isEmpty()) {
            Out.println(message);
            Out.println();
        }
    }

    /** @return the next String give by the player */
    protected String nextString(String message) {
        return In.nextString(message + " (chaîne de caractère) : ");
    }

    /** @return the next Int give by the player */
    protected int nextInt(String message, boolean hasMin, int min, boolean hasMax, int max) {
        return In.nextInt(message + " (nombre entier) : ", hasMin, min, hasMax, max);
    }

    /** interact with the player */
    protected abstract void interact();
}
