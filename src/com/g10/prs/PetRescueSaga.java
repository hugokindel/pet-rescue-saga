package com.g10.prs;

import com.g10.prs.core.Resources;
import com.g10.prs.core.njson.NJson;
import com.g10.prs.core.njson.NJsonReader;
import com.g10.prs.core.njson.NJsonWriter;
import com.g10.prs.core.options.Command;
import com.g10.prs.core.options.Runnable;
import com.g10.prs.level.Level;
import com.g10.prs.view.ui.*;
import com.g10.prs.view.View;
import com.g10.prs.player.Player;
import com.g10.prs.view.cli.CliView;

import java.util.*;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    public static Player player = new Player();
    public static View view = new CliView();

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);

        view.run();

        /*if (!showHelp && !showVersion) {
            while (true) {
                int result = showMenu(currentMenu);

                if (result == -1) {
                    sc.close();
                    return 0;
                } else if (result == 0) {
                    currentMenu = menuBacklog.pop();
                }
            }
        }*/

        return 0;
    }
}
