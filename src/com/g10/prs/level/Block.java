package com.g10.prs.level;

/** Cell that represents a blocK. */
public class Block extends Cell {
    /** The type of block. */
    private BlockType blockType;

    /**
     * Class constructor.
     *
     * @param blockType The type of block.
     */
    public Block(BlockType blockType) {
        super(CellType.Block);
        this.blockType = blockType;
    }

    /** @return the type of block. */
    public BlockType getBlockType() {
        return blockType;
    }
}
