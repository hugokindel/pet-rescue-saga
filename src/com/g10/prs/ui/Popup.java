package com.g10.prs.ui;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.core.printer.In;
import com.g10.prs.core.printer.Out;

import java.util.Scanner;

public class Popup {
    private String title;
    private String description;
    private PopupType type;

    public Popup(String title, String description, PopupType type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String use() {
        Out.println("--------------------------------------------------------------------------------");
        Out.clear();
        Out.println(title);
        Out.println();

        String result = "";
        if (type == PopupType.ReturnInt) {
            result = String.valueOf(In.nextInt(description));
        } else if (type == PopupType.ReturnString) {
            result = In.nextString(description);
        }

        return result;
    }
}