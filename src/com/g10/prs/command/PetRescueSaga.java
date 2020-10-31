package com.g10.prs.command;

import com.g10.prs.cli.annotation.Command;
import com.g10.prs.cli.annotation.Option;
import com.g10.prs.cli.type.Runnable;

@Command(name = "prs", version = "1.0.0", description = "A game about rescuing animals.")
public class PetRescueSaga extends Runnable {
    /** Class constructor. */
    public PetRescueSaga() {

    }

    public int run(String[] args) {
        readArguments(args, PetRescueSaga.class);

        return 0;
    }
}
