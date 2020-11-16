package com.g10.prs;

import com.g10.prs.core.options.annotation.Command;
import com.g10.prs.core.options.type.Runnable;
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
        Scanner sc = new Scanner(System.in);

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
        }

        /*try {
            Level level = Level.load("campaign/level_01.njson");
            level.print();
            level.removeGameMode(0, 3, true);
            level.print();
            level.removeGameMode(0, 3, true);
            level.print();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return 0;
    }

    private static int showMenu(Menu menu) {
        return currentMenu.use();
    }
}
