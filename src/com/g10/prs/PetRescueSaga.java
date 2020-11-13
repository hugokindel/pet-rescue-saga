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
    public static Element currentElement;
    public static Stack<Menu> elementsUndoList;
    public static Player player;

    public static int showMenu(Menu element, boolean addCurrentMenuToBacklog) {
        if (addCurrentMenuToBacklog) {
            elementsUndoList.push((Menu)currentElement);
        }

        currentElement = element;

        return (int)currentElement.use();
    }

    public static String showPopup(Popup popup) {
        elementsUndoList.push((Menu)currentElement);
        currentElement = popup;
        return ((Popup)currentElement).use();
    }

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);

        if (!showHelp && !showVersion) {
            while (true) {
                int result = showMenu(new MainMenu(), false);

                if (result == -1) {
                    return 0;
                } else if (result == 0) {
                    currentElement = elementsUndoList.pop();
                }
            }

            /**
             Player player = new Player();
             boolean verif = true;
             Scanner sc = new Scanner(System.in);
             while (verif) {
                 Out.println("Welcome " + player.getName() + " , what do you wnat to do ?");
                 Out.println("(p) Play ?  /   (q) Leave ?  /  (n) Choose a different name ?");
                 char result = player.choice(sc);
                 switch (result) {
                 case 'p':
                     verif = false;
                     // Level with Player in param to change the player stat
                     break;
                 case 'q':
                     sc.close();
                     return 0;
                 case 'n':
                     player.newName(sc); //Redo the same Question
                     break;
                 default:
                     Out.println("Wrong char");
                     break;
                 }
             } */

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
