package com.g10.prs.AI;

import com.g10.prs.PetRescueSaga;
import com.g10.prs.common.Pair;
import com.g10.prs.common.Triplet;
import com.g10.prs.common.print.Out;
import com.g10.prs.level.Block;
import com.g10.prs.level.Cell;
import com.g10.prs.level.CellType;
import com.g10.prs.level.Level;

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
     * @param board The actual board of the level
     * @return a pair with the coordonne of the block to destroy
     */
    public Pair<Integer,Integer> play(Cell[][] board){
        ArrayList<Triplet<Integer,Integer,Double>> list = createList(board);
        list = scoreAdj(board,list);
        //list = scoreAnimal(PetRescueSaga.level,list); fixme
        return bestChoice(list);
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
            for(int v=0;v<board[i].length;v++){
                res[i][v] = board[i][v];
            }
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

    //fixme
/**
    public ArrayList<Triplet<Integer,Integer,Double>> scoreAnimal(Level level, ArrayList<Triplet<Integer,Integer,Double>> list){
        ArrayList<Triplet<Integer,Integer,Double>> l = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Level tmp = level.copy();
            int r = list.get(i).getObject1();
            int c = list.get(i).getObject2();
            int animalBefore = tmp.getAnimalsLeft();
            tmp.removeGameMode(c,r,true);
            int animalAfter = tmp.getAnimalsLeft();
            l.add(new Triplet<>(r,c,(list.get(i).getObject3()+(animalBefore-animalAfter))));
        }
        return l;
    }
*/
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
