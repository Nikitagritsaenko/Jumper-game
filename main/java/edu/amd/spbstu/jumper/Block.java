package edu.amd.spbstu.jumper;

import java.util.ArrayList;



public class Block {
    private BlockType type;
    private int degree;
    private int x, y, width = 64, height = 64;

    public Block(BlockType type, int x, int y) {
        this.type = type;
        if (type == BlockType.DESTROYABLE) {
            degree = 1; //////
        }
        else {
            degree = Integer.MAX_VALUE;
        }
        this.x = x;
        this.y = y;
    }

    public BlockType getType() {
        return type;
    }

    public int getDegree() {
        return degree;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


