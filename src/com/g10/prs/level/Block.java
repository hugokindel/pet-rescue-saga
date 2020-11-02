package com.g10.prs.level;

public class Block extends Cell {
    private BlockType blockType;

    public Block(BlockType blockType) {
        super(CellType.Block);
        this.blockType = blockType;
    }

    public BlockType getBlockType() {
        return blockType;
    }
}
