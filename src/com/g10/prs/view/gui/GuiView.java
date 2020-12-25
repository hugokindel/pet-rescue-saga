package com.g10.prs.view.gui;

import com.g10.prs.view.Menu;
import com.g10.prs.view.View;

public class GuiView extends View {
    private Window window;

    public GuiView() {
        super(new MainMenu());
    }

    @Override
    public void run() {
        window = new Window();

        window.setLocationRelativeTo(null);
        currentMenu.draw();
        window.setVisible(true);
    }

    public Window getWindow() {
        return window;
    }

    @Override
    public void changeMenu(Menu menu, boolean addCurrentToBacklog) {
        window.getContentPane().removeAll();

        super.changeMenu(menu, addCurrentToBacklog);

        currentMenu.draw();
        window.validate();
    }

    @Override
    public void goBack() {
        changeMenu(menuBacklog.pop(), false);
    }
}
