package edu.amd.spbstu.jumper;


public class Block {
    private BlockType type;
    private int degree;
    private int x, y;
    private double initVelocity;

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
        this.initVelocity = 30.0;
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

    public double getInitVelocity() {
        return initVelocity;
    }
}


