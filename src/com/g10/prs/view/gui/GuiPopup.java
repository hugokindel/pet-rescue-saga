package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;

import javax.swing.*;

/** base class for Gui Popup */
public abstract class GuiPopup {
    /** title of the Popup */
    protected final String title;
    /** message of the Popup */
    protected final String message;

    /** class constructor */
    public GuiPopup(String title, String message) {
        this.title = title;
        this.message = message;
    }

    /** class constructor */
    public GuiPopup(String title) {
        this(title, "");
    }

    /** show the popup */
    public static void show(GuiPopup popup) {
        popup.interact();
    }

    /** @return the next string give by the player */
    protected String nextString(String message) {
        while (true) {
            String s = (String) JOptionPane.showInputDialog(
                    getWindow(),
                    (this.message.isEmpty() ? "" : this.message + "\n") + message,
                    title,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            if (s == null) {
                return null;
            }

            if (s.length() > 0) {
                return s;
            }
        }
    }

    /** interact with the player */
    protected abstract void interact();

    /** @return the window */
    public Window getWindow() {
        return ((GuiView)PetRescueSaga.view).getWindow();
    }
}
