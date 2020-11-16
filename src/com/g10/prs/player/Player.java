package com.g10.prs.player;

/** Structure for a class. */
public class Player {
    /** The nme of the player */
    private String name;

    /** Class constructor. */
    public Player() {
        name = "Player";
    }

    /**
     * Sets the name of the player.
     *
     * @param name The name to use.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the name of the player. */
    public String getName() {
        return name;
    }
}