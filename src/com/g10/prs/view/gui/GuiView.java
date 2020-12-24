package com.g10.prs.view.gui;

import com.g10.prs.view.View;

public class GuiView extends View {
    private Window window;
    boolean event = true;

    public GuiView() {
        super(new MainMenu());
        window = new Window();
        window.setVisible(true);
    }

    @Override
    public void run() {

    }

    public Window getWindow() {
        return window;
    }
}
