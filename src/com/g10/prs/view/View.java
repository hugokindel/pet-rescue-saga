package com.g10.prs.view;

import com.g10.prs.view.ui.MainMenu;
import com.g10.prs.view.ui.Menu;
import com.g10.prs.view.ui.Popup;

import java.util.Stack;

public abstract class View {
    public Menu currentMenu;
    public Stack<Menu> menuBacklog;
    private boolean quit;

    public View() {
        currentMenu = new MainMenu();
        menuBacklog = new Stack<Menu>();
        quit = false;
    }

    public void showMenu(Menu menu) {
        if (currentMenu != null) {
            menuBacklog.push(currentMenu);
        }

        currentMenu = menu;
    }

    public void showPopup(Popup popup){
        showPopup(popup,true);
    }

    public abstract void showPopup(Popup popup,boolean v);

    public abstract int nextAnswer();

    public abstract String nextString();

    public abstract int nextInt();

    public void run() {
        start();

        while (!quit) {
            currentMenu.resetAnswer();
            drawMenu(currentMenu);
            int result = currentMenu.getAnswer();

            if (result == -1) {
                quit = true;
            } else if (result == 0) {
                currentMenu = menuBacklog.pop();
            } else {
                currentMenu.handleAnswer();
            }
        }

        end();
    }

    protected void start() {

    }

    protected void end() {

    }

    protected abstract void drawMenu(Menu menu);
}
