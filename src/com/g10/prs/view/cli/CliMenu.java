package com.g10.prs.view.cli;

import com.g10.prs.common.print.In;
import com.g10.prs.common.print.Out;
import com.g10.prs.view.Menu;

public abstract class CliMenu extends Menu {
    protected String[] categories;

    public CliMenu(String title, String[] categories, boolean canGoBack) {
        super(title, canGoBack);

        this.categories = categories;
    }

    public CliMenu(String title, String[] categories) {
        this(title, categories, true);
    }

    public CliMenu(String title) {
        this(title, null, true);
    }


    public String[] getCategories() {
        return categories;
    }

    public void draw() {
        drawBorder();
        drawPrimary();
        drawTitle();
        drawContent();
        drawCategories();
    }

    protected void drawBorder() {
        Out.println("--------------------------------------------------------------------------------");
        Out.clear();
    }

    protected void drawPrimary() {

    }

    protected void drawTitle() {
        Out.println(title.toUpperCase());
        Out.println();
    }

    protected void drawContent() {

    }

    protected void drawCategories() {
        if (categories != null) {
            for (int i = 0; i < categories.length; i++) {
                Out.println(i + 1 + ". " + categories[i]);
            }
            Out.println();
        }
    }

    public int getChoice() {
        String description = "";

        if (categories != null) {
            description = "Sélectionnez une catégorie du menu" + (canGoBack ? ", 'q' pour quitter ou 'b' pour revenir au menu précédent" : " ou 'q' pour quitter") + " : ";
        } else {
            description = "Sélectionnez 'q' pour quitter" + (canGoBack ? " ou 'b' pour revenir au menu précédent" : "") + " : ";
        }

        return In.nextAnswer(description, canGoBack, categories != null ? categories.length : 0);
    }

    public void handleChoice(int choice) {

    }
}
