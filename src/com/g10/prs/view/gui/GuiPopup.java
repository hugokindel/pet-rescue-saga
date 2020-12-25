package com.g10.prs.view.gui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;

import javax.swing.*;

public abstract class GuiPopup {
    protected final String title;
    protected final String message;

    public GuiPopup(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public GuiPopup(String title) {
        this(title, "");
    }

    public static void show(GuiPopup popup) {
        popup.interact();
    }

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

    protected abstract void interact();

    public Window getWindow() {
        return ((GuiView)PetRescueSaga.view).getWindow();
    }
}
