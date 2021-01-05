package com.g10.prs.view.cli;

import com.g10.prs.common.print.Color;
import com.g10.prs.common.print.Out;
import com.g10.prs.common.print.TextColor;

/** Menu with the rules of the game */
public class RulesMenu extends CliMenu {

    /** class constructor */
    public RulesMenu() {
        super("Règles du jeu");
    }

    /** draw the content (rule) */
    @Override
    protected void drawContent() {
        Out.println("Voici comment jouer à " + TextColor.Red + "Pet Rescue Saga" + Color.ResetAll + " :");
        Out.println();
        Out.println("Votre but est de " + TextColor.Red + "sauver" + Color.ResetAll + " tous les animaux (indiqués par des lettres sur la carte du niveau).");
        Out.println("Pour le faire, vous devez " + TextColor.Red + "enlever" + Color.ResetAll + " les blocs colorés, ce qui va aussi affecter votre score final.");
        Out.println("N'oubliez pas de tenir compte de la gravité ! Les blocs tombent vers le bas et se déplace vers la gauche lorsqu'ils sont sur un obstacle et que la voie est libre.");
        Out.println("Il est aussi possible d'utiliser la " + TextColor.Red + "fusée" + Color.ResetAll + " pour supprimer tous les blocs d'une colonne ou le " +
                TextColor.Red + "sabre" + Color.ResetAll + " pour supprimer tous les blocs d'une ligne.");
        Out.println("Attention, vous ne pouvez pas supprimer les blocs de couleurs qui ne sont pas au moins par groupe de 2 !");
        Out.println();
        Out.println("Le fonctionnement du score est le suivant :");
        Out.println(TextColor.Red + "+ 100" + Color.ResetAll + " - pour chaque bloc enlevé.");
        Out.println(TextColor.Red + "+ 1000" + Color.ResetAll + " - pour chaque animal sauvé.");
        Out.println(TextColor.Red + "+ 5000" + Color.ResetAll + " - lorsque la partie est gagné.");
        Out.println();
        Out.println("Bon jeu !");
        Out.println();
    }
}
