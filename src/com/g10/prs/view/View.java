package com.g10.prs.view;

import java.util.Stack;

/** base class of every view */
public class View {
    /** current menu */
    protected Menu currentMenu;

    /** Stack of all the previous menu */
    protected Stack<Menu> menuBacklog;

    /** to quit the view */
    protected boolean quit;

    /** class constructor */
    public View(Menu defaultMenu) {
        currentMenu = defaultMenu;
        menuBacklog = new Stack<>();
        quit = false;
    }

    /** to quit the view */
    public void quit() {
        quit = true;
    }

    /** to go to the previous menu */
    public void goBack() {
        if (!menuBacklog.empty()) {
            currentMenu = menuBacklog.pop();
        }
    }

    /** to run the game */
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

    /**
     *  change the current menu
     *
     * @param addCurrentToBacklog decide if we can go back to the previous menu 
     */
    public void changeMenu(Menu menu, boolean addCurrentToBacklog) {
        if (addCurrentToBacklog) {
            menuBacklog.push(currentMenu);
        }

        currentMenu = menu;
    }

    /** change the current menu with the possibility to go back */
    public void changeMenu(Menu menu) {
        changeMenu(menu, true);
    }
}
