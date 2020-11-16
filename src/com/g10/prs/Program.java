package com.g10.prs;

import com.g10.prs.core.printer.Out;

/** This is main class of the cli program. */
public class Program {
    /** The instance of the game. */
    public static PetRescueSaga prs;

    /** Start of the program. */
    public static void main(String[] args) {
        Out.start();
        prs = new PetRescueSaga();
        int errorCode = prs.run(args);
        Out.end();
        System.exit(errorCode);
    }
}
