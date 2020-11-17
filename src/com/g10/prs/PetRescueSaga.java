package com.g10.prs;

import com.g10.prs.level.Level;
import com.g10.prs.option.Command;
import com.g10.prs.option.Option;
import com.g10.prs.option.Runnable;
import com.g10.prs.common.print.Out;
import com.g10.prs.view.graphic.SwingView;
import com.g10.prs.view.View;
import com.g10.prs.player.Player;
import com.g10.prs.view.cli.CliView;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    @Option(names = {"--view"}, description = "Select which type of view to show", usage = "<gui> or <cli>")
    protected String viewType;

    public static Player player = new Player();

    public static View view;

    public static Level level;

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);

        if (viewType != null && !viewType.equals("cli") && !viewType.equals("gui")) {
            Out.println("Unknown view!");
            return 1;
        }

        if (!showHelp && !showVersion) {
            if (viewType == null || viewType.equals("cli")) {
                view = new CliView();
            } else {
                view = new SwingView();
            }

            view.run();
        }

        return 0;
    }
}
