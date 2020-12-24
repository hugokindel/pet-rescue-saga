package com.g10.prs;

import com.g10.prs.common.print.Out;

/** This is main class of the cli program. */
public class Program {
    /** The instance of the game. */
    public static PetRescueSaga prs;

    /** Start of the program. */
    public static void main(String[] args) {
        Out.start();

        prs = new PetRescueSaga();
        int errorCode = prs.run(args);

        if (!inGuiView(args)) {
            Out.end();
            System.exit(errorCode);
        }
    }

    /** @return if we are in gui view or not. */
    private static boolean inGuiView(String[] args) {
        for (String arg : args) {
            if (arg.contains("=gui")) {
                return true;
            }
        }

        return false;
    }
}
