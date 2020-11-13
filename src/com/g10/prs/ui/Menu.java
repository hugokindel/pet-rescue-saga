package com.g10.prs.ui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.core.printer.Out;

import java.util.Scanner;

public class Menu implements Element<Integer> {
    private String title;
    private boolean canGoBack;
    private String[] categories;

    public Menu(String title, boolean canGoBack, String[] categories) {
        this.title = title;
        this.canGoBack = canGoBack;
        this.categories = categories;
    }

    public Integer use(Scanner sc) {
        Out.println(title);
        Out.println();
        for (int i = 0; i<categories.length ; i++) {
            Out.println(i+1 + ". " + categories[i]);
        }
        Out.println();
        if (canGoBack) {
            Out.print("Sélectionnez une catégorie du menu, 'q' pour quitter ou 'b' pour revenir au menu précédent: ");
        } else {
            Out.print("Sélectionnez une catégorie du menu ou 'q' pour quitter: ");
        }
        String result = PetRescueSaga.nextString(sc);
        Out.println();
        return PetRescueSaga.nextAnswer(result,canGoBack);
    }
}