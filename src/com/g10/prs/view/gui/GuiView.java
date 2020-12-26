package com.g10.prs.view.gui;

import com.g10.prs.view.Menu;
import com.g10.prs.view.View;

/** Gui view class */
public class GuiView extends View {
    /** window of the game */
    private Window window;

    /** class constructor */
    public GuiView() {
        super(new MainMenu());
    }

    /** show the window */
    @Override
    public void run() {
        window = new Window();

        window.setLocationRelativeTo(null);
        currentMenu.draw();
        window.setVisible(true);
    }

    /** @return the window */
    public Window getWindow() {
        return window;
    }

    /** change the current menu */
    @Override
    public void changeMenu(Menu menu, boolean addCurrentToBacklog) {
        window.getContentPane().removeAll();

        super.changeMenu(menu, addCurrentToBacklog);

        currentMenu.draw();
        window.validate();
    }

    /** go to the previous menu */
    @Override
    public void goBack() {
        changeMenu(menuBacklog.pop(), false);
    }

    /** reload the current menu */
    public void reload() {
        changeMenu(currentMenu, false);
    }
}
