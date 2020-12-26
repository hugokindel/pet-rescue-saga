package com.g10.prs.view.cli;

import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;
import com.g10.prs.view.Menu;

/** base class for Cli Menu */
public abstract class CliMenu extends Menu {
    /** the action that can be made in the menu */
    protected String[] categories;

    /** class constructor */
    public CliMenu(String title, String[] categories, boolean canGoBack) {
        super(title, canGoBack);

        this.categories = categories;
    }

    /** class constructor */
    public CliMenu(String title, String[] categories) {
        this(title, categories, true);
    }

    /** class constructor */
    public CliMenu(String title) {
        this(title, null, true);
    }


    /** @return the categories of the menu */
    public String[] getCategories() {
        return categories;
    }

    /** draw the menu */
    public void draw() {
        drawBorder();
        drawPrimary();
        drawTitle();
        drawContent();
        drawCategories();
    }

    /** draw the border of the menu */
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

    /** draw the categories */
    protected void drawCategories() {
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                Out.println(i + 1 + ". " + categories[i]);
            }
            Out.println();
        }
    }

    /** @return the next Int give by the player */
    public int getChoice() {
        String description = "";

        if (categories != null) {
            description = "Sélectionnez une catégorie du menu" + (canGoBack ? ", 'q' pour quitter ou 'b' pour revenir au menu précédent" : " ou 'q' pour quitter") + " : ";
        } else {
            description = "Sélectionnez 'q' pour quitter" + (canGoBack ? " ou 'b' pour revenir au menu précédent" : "") + " : ";
        }

        return In.nextAnswer(description, canGoBack, categories != null ? categories.length : 0);
    }

    /** handle the answer of the player */
    public void handleChoice(int choice) {

    }
}
