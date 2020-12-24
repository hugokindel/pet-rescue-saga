package com.g10.prs.view;

import java.util.Stack;

public class View {
    protected Menu currentMenu;
    protected Stack<Menu> menuBacklog;
    protected boolean quit;

    public View(Menu defaultMenu) {
        currentMenu = defaultMenu;
        menuBacklog = new Stack<>();
        quit = false;
    }

    public void quit() {
        quit = true;
    }

    public void goBack() {
        if (!menuBacklog.empty()) {
            currentMenu = menuBacklog.pop();
        }
    }

    public void run() {
        while (!quit) {
            currentMenu.draw();
            int choice = currentMenu.getChoice();

            if (choice == -1) {
                quit();
            } else if (choice == 0) {
                goBack();
            } else {
                currentMenu.handleChoice(choice);
            }
        }
    }

    public void changeMenu(Menu menu, boolean addCurrentToBacklog) {
        if (addCurrentToBacklog) {
            menuBacklog.push(currentMenu);
        }

        currentMenu = menu;
    }

    public void changeMenu(Menu menu) {
        changeMenu(menu, true);
    }
}
