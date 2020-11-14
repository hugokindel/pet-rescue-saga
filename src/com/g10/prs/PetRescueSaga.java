package com.g10.prs;

import com.g10.prs.core.cli.annotation.Command;
import com.g10.prs.core.cli.type.Runnable;
import com.g10.prs.core.printer.Out;
import com.g10.prs.core.resource.NJsonReader;
import com.g10.prs.ui.*;
import com.g10.prs.view.ViewType;
import com.g10.prs.player.Player;

import java.util.*;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    public static ViewType view;
    public static Player player = new Player();
    public static Menu currentMenu = new MainMenu();
    public static Stack<Menu> menuBacklog = new Stack<>();

    public static String showPopup(String title, String description, PopupType type) {
        Popup popup = new Popup("Changer de nom","Veuillez entrez votre nouveau nom: ",PopupType.ReturnString);
        return popup.use();
    }

    public static void setNextMenu(Menu menu) {
        if (currentMenu != null) {
            menuBacklog.push(currentMenu);
        }

        currentMenu = menu;
    }

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);
        /*Scanner sc = new Scanner(System.in);

        if (!showHelp && !showVersion) {
            while (true) {
                int result = showMenu(currentMenu);

                if (result == -1) {
                    sc.close();
                    return 0;
                } else if (result == 0) {
                    currentMenu = menuBacklog.pop();
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
            }
        }*/

        try {
            Map<String, Object> fileContent = new NJsonReader().parse("test.njson");
            Out.println(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int showMenu(Menu menu) {
        return currentMenu.use();
    }
}
