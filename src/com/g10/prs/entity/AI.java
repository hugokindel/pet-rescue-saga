package com.g10.prs.entity;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.common.Triplet;
import com.g10.prs.common.print.Out;
import com.g10.prs.level.*;

import java.util.ArrayList;

/** Structure for a class. */
public class AI {
    /** The nme of the AI */
    private String name;

    /** Class constructor. */
    public AI() {
        name = "Bot";
    }

    /** @return the name of the AI. */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the AI.
     *
     * @param name The name to use.
     */
    public void setName(String name) {
            this.name = name;
        }

    /**
     * calculate the best choice
     *
     * @param level The level
     * @return a pair with the coordonne of the block to destroy
     */
    public void play(Level level){
        ArrayList<Triplet<Integer, Integer, Double>> list = createList(level.getBoard());
        list = scoreAdj(copy(level.getBoard()), list);
        list = scoreAnimal(level.copy(), list);
        list = scoreAlea(list);
        Pair<Integer, Integer> pair = bestChoice(list);
        PetRescueSaga.level.removeGameMode(pair.getObject2(), pair.getObject1(), true, true);
    }

    /**
     * create a list of all the block
     *
     * @param board The actual board of the level
     * @return an ArrayList of Triplet
     */
    public ArrayList<Triplet<Integer,Integer,Double>> createList(Cell[][] board){
        ArrayList<Triplet<Integer,Integer,Double>> list = new ArrayList<>();
        for (int i = 0;i<board.length;i++){
            for (int v = 0;v<board[i].length;v++){
                if(board[i][v] != null && board[i][v].getType() == CellType.Block){
                    list.add(new Triplet<>(i,v,0.0));
                }
            }
        }
        return list;
    }

    /**
     * calculate the score of each block
     *
     * @param board The actual board of the level
     * @param list list of all the block
     * @return a list of all the block with a new score
     */
    public ArrayList<Triplet<Integer,Integer,Double>> scoreAdj(Cell[][] board,ArrayList<Triplet<Integer,Integer,Double>> list){
        ArrayList<Triplet<Integer,Integer,Double>> l = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            int r = list.get(i).getObject1();
            int c = list.get(i).getObject2();
            Block block = (Block)board[r][c];
            l.add(new Triplet<>(r,c,scoreAdjAux(copy(board),block,r,c)));
        }
        return l;
    }

    /**
     * copy of the board
     *
     * @param board The actual board of the level
     * @return a copy
     */
    public Cell[][] copy(Cell[][] board){
        Cell[][] res = new Cell[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
            System.arraycopy(board[i], 0, res[i], 0, board[i].length);
        }
        return res;
    }

    /**
     * calculate the score for one block
     *
     * @param board The actual board of the level
     * @param block the block we want to calculate the score
     * @param r coordonne of the block in the board
     * @param c coordonne of the block in the board
     * @return the score
     */
    public Double scoreAdjAux(Cell[][] board,Block block,int r,int c){
        Double res = 0.1;
        board[r][c] = null;
        if (r - 1 >= 0 && board[r - 1][c] instanceof Block &&
                ((Block)board[r - 1][c]).getBlockType() == block.getBlockType()) {
            res += scoreAdjAux(board, block, r - 1, c);
        }

        if (r + 1 < board.length && board[r + 1][c] instanceof Block &&
                ((Block)board[r + 1][c]).getBlockType() == block.getBlockType()) {
            res += scoreAdjAux(board, block, r + 1, c);
        }

        if (c - 1 >= 0 && board[r][c - 1] instanceof Block &&
                ((Block)board[r][c - 1]).getBlockType() == block.getBlockType()) {
            res += scoreAdjAux(board, block, r, c - 1);
        }

        if (c + 1 < board[r].length && board[r][c + 1] instanceof Block &&
                ((Block)board[r][c + 1]).getBlockType() == block.getBlockType()) {
            res += scoreAdjAux(board, block, r, c + 1);
        }
        return res;
    }


    /**
     * calculate the score of each block
     *
     * @param level The level
     * @param list list of all the block
     * @return a list of all the block with a new score
     */
    public ArrayList<Triplet<Integer,Integer,Double>> scoreAnimal(Level level, ArrayList<Triplet<Integer,Integer,Double>> list){
        ArrayList<Triplet<Integer,Integer,Double>> l = new ArrayList<>();

        for (Triplet<Integer, Integer, Double> element : list) {
            Level tmp = level.copy();
            int r = element.getObject1();
            int c = element.getObject2();
            int animalBefore = tmp.getAnimalsLeft();

            if (level.getBoard()[r][c] instanceof Animal) {
                continue;
            }

            tmp.removeGameMode(c, r, true, false);
            int animalAfter = tmp.getAnimalsLeft();
            l.add(new Triplet<>(r, c, (element.getObject3() + (animalBefore - animalAfter))));
        }

        return l;
    }

    /**
     * calculate the score of each block with random numbers
     *
     * @param list list of all the block
     * @return a list of all the block with a new score
     */
    public ArrayList<Triplet<Integer,Integer,Double>> scoreAlea(ArrayList<Triplet<Integer,Integer,Double>> list){
        ArrayList<Triplet<Integer,Integer,Double>> l = new ArrayList<>();

        for (Triplet<Integer, Integer, Double> element : list) {
            double tmp = Math.random()*0.5;
            l.add(new Triplet<>(element.getObject1(), element.getObject2(), (element.getObject3()+tmp)));
        }

        return l;
    }

    /**
     * Choose the block with the highest score
     *
     * @param list The list of all the block
     * @return a Pair of the coordonne of the best block
     */
    public Pair<Integer,Integer> bestChoice(ArrayList<Triplet<Integer,Integer,Double>> list){
        int best = 0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getObject3() > list.get(best).getObject3()) best = i;
        }
        return new Pair<Integer, Integer>(list.get(best).getObject1(),list.get(best).getObject2());
    }

}
