package com.g10.prs.ui;

import com.g10.prs.core.printer.In;
import com.g10.prs.core.printer.Out;

public class Menu {
    private String title;
    private boolean canGoBack;
    private String[] categories;

    public Menu(String title, boolean canGoBack, String[] categories) {
        this.title = title;
        this.canGoBack = canGoBack;
        this.categories = categories;
    }

    public Integer use() {
        return use(false);
    }

    public Integer use(boolean customClear) {
        if (!customClear) {
            Out.println("--------------------------------------------------------------------------------");
            Out.clear();
        }

        Out.println(title);
        Out.println();
        for (int i = 0; i < categories.length; i++) {
            Out.println(i + 1 + ". " + categories[i]);
        }
        Out.println();
        String description = "Sélectionnez une catégorie du menu" + (canGoBack ? ", 'q' pour quitter ou 'b' pour revenir au menu précédent" : " ou 'q' pour quitter") + ": ";
        return In.nextAnswer(description, canGoBack, categories.length);
    }
}