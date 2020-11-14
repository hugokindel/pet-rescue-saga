package com.g10.prs.core.printer;

import com.g10.prs.core.type.PrsException;

import java.util.Scanner;

public class In {
    private static Scanner sc = new Scanner(System.in);

    public static String nextString(String message) {
        String ret;

        while (true) {
            ret = next(message);

            if (!ret.isEmpty()) {
                return ret;
            } else {
                Out.println("Cette valeur n'est pas une chaîne de caractères !");
            }
        }
    }

    public static int nextInt(String message) {
        int ret;

        while (true) {
            String result = next(message);

            try {
                ret = Integer.parseInt(result);
                return ret;
            } catch (Exception e) {
                Out.println("Cette valeur n'est pas un nombre entier !");
            }
        }
    }

    public static int nextAnswer(String message, boolean canGoBack, int max) {
        int ret;

        while (true) {
            String result = next(message);

            if (result.length() == 1 && result.charAt(0) == 'q') {
                return -1;
            } else if (canGoBack && result.length() == 1 && result.charAt(0) == 'b') {
                return 0;
            } else {
                try {
                    ret = Integer.parseInt(result);

                    if (ret < 1 || ret > max) {
                        throw new PrsException("Réponse invalide !");
                    }

                    return ret;
                } catch (Exception e) {
                    Out.println("Cette valeur n'est pas une réponse valide !");
                }
            }
        }
    }

    private static String next(String description) {
        String ret;
        Out.print(description);
        ret = sc.nextLine().trim();
        Out.printToFile(ret.length() == 0 ? "<empty>" : ret);
        Out.simulateNewLine();
        return ret;
    }
}
