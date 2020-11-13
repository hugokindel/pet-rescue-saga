package com.g10.prs.player;

import com.g10.prs.core.printer.Out;

import java.util.Scanner;

public class Player {
    private String name;

    public Player(){
        name = "Player";
    }

    public String getName(){
        return name;
    }

    public void setName(String s){
        name = s;
    }

    public char choice(Scanner sc){
        if(sc.hasNext()) {
            char result = sc.next().charAt(0);
            return result;
        }
        return 'e';
    }

}