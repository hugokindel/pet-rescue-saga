package com.g10.prs;

import com.g10.prs.core.cli.annotation.Command;
import com.g10.prs.core.cli.type.Runnable;
import com.g10.prs.core.printer.BackgroundColor;
import com.g10.prs.core.printer.TextColor;
import com.g10.prs.core.type.PrsException;
import com.g10.prs.level.*;
import com.g10.prs.ui.Element;
import com.g10.prs.ui.MainMenu;
import com.g10.prs.ui.Menu;
import com.g10.prs.ui.Popup;
import com.g10.prs.view.ViewType;
import com.g10.prs.core.printer.Out;
import com.g10.prs.player.Player;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    public static ViewType view;
    public static Player player = new Player();
    public static Element currentElement = new MainMenu();
    public static Stack<Menu> elementsUndoList = new Stack<>();

    public static int showMenu(Menu element, boolean addCurrentMenuToBacklog,Scanner sc) {
        if (addCurrentMenuToBacklog) {
            elementsUndoList.push((Menu)currentElement);
        }

        currentElement = element;

        return (int)currentElement.use(sc);
    }

    public static void placeMenu(Menu element, boolean addCurrentMenuToBacklog){
        if (addCurrentMenuToBacklog) {
            elementsUndoList.push((Menu)currentElement);
        }

        currentElement = element;
    }

    public static String nextString(Scanner sc){
        return sc.next();
    }

    public static int nextAnswer(String s, boolean v){
        if (s == null) return -2;
        if (s.charAt(0) == 'q') return -1;
        if (v && s.charAt(0) == 'b') return 0;
        try {
            int i = Integer.parseInt(s);
            return i;
        } catch (Exception e) {
            return -2;
        }
    }

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);
        Scanner sc = new Scanner(System.in);

        if (!showHelp && !showVersion) {
            while (true) {
                int result = showMenu((Menu)currentElement, false,sc);

                if (result == -1) {
                    sc.close();
                    return 0;
                } else if (result == 0) {
                    currentElement = elementsUndoList.pop();
                }
            }


            /*try {
                Level level = new Level("1.level");
                Out.println("Level: " + level.getName());
                level.print();
                Out.println("Remove (0,4)");
                level.remove(0, 4);
                Out.println("Remove (0,5)");
                level.remove(0, 5);
                Out.println("Remove (1,4)");
                level.remove(1, 4);
                Out.println("Remove (3,5)");
                level.remove(3,5);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        return 0;
    }
}
