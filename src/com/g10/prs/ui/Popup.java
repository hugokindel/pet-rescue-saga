package com.g10.prs.ui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.core.printer.Out;

import java.util.Scanner;

public class Popup implements Element<String> {
    private String title;
    private String description;
    private PopupType type;

    public Popup(String title, String description, PopupType type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String use(Scanner sc) {
        Out.println(title);
        Out.println();
        Out.print(description);
        if ( type == PopupType.ReturnInt) {
            String result = PetRescueSaga.nextString(sc);
            Out.println();
            try {
                int i = Integer.parseInt(result);
            } catch (Exception e) {
                Out.println("Ceci n'est pas une bonne valeur");
                return "";
            }
            return result;
        } else if (type == PopupType.ReturnString) {
            String result = PetRescueSaga.nextString(sc);
            Out.println();
            return result;
        }
        return "";
    }
}