package com.g10.prs.view.gui;

import com.g10.prs.view.View;

public class GuiView extends View {
    private Window window;

    public GuiView() {
        super(new MainMenu());
    }

    @Override
    public void run() {
        window = new Window();

        currentMenu.draw();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public Window getWindow() {
        return window;
    }
}
