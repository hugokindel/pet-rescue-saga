package com.g10.prs.game.level;
import java.util.Random;

public class Level {

    private int score;
    private int height;
    private int length;
    /**
     * 0 ==> obstacle
     * 1 to 5 ==> color
     * 6 ==> animal
     * 7 ==> empty
     */
    private int[][] state;
    private boolean[][] animal;

    public Level(int h, int l){
        score = 0;
        height = h;
        length = l;
        state = new int[height + 2][length + 2];
        animal = new boolean[height + 2][length + 2];
    }

    public void addAnimal(int y, int x){
        if(!animal[y][x]){
            animal[y][x] = true;
            state[y][x] = 6;
        }
    }

    public void addAnimal(int[] p){
        if(p.length % 2 == 0) {
            for (int i = 0; i < p.length; i = i + 2) {
                if(!animal[i][i+1]){
                    animal[i][i+1] = true;
                    state[i][i+1] = 6;
                }
            }
        }
    }

    public void addState(int y, int x, int s){
        if(s != 6){
            state[y][x] = s;
        }
    }

    public void addState(int[] p, int s){
        if(s != 6){
            if(p.length % 2 == 0) {
                for (int i = 0; i < p.length; i = i + 2) {
                    state[i][i + 1] = s;
                }
            }
        }
    }

    public void explodeCase(int y, int x){
        int c = state[y][x];
        if(c != 0 && c != 6 && c != 7) {
            state[y][x] = 7;
            score += 100;

            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                for (int x1 = x - 1; x1 <= x + 1; x1++) {
                    if (state[y1][x1] == c) {
                        explodeCase(y1, x1);
                    }
                }
            }
        }
    }

    public boolean verif(int y, int x){
        int c = state[y][x];
        if(c != 0 && c != 6 && c != 7) {

            for (int y1 = y - 1; y1 <= y + 1; y1++) {
                for (int x1 = x - 1; x1 <= x + 1; x1++) {
                    if (state[y1][x1] == c) {
                        return true;
                    }
                }
            }
            return false;

        } else {
            return false;
        }
    }

}
