package edu.amd.spbstu.jumper;


import android.graphics.Bitmap;

import java.util.ArrayList;

import static java.lang.StrictMath.abs;

public class Block {
    private BlockType type;
    private int degree;
    private int x, y;
    private int idx;
    private double initVelocity;

    public Block(BlockType type, int x, int y) {
        this.type = type;

        if (type == BlockType.EMPTY)
            degree = 0;
        else if (type == BlockType.DESTROYABLE_1)
            degree = 1;
        else if (type == BlockType.DESTROYABLE_2)
            degree = 2;
        else
            degree = Integer.MAX_VALUE;

        this.x = x;
        this.y = y;
        this.initVelocity = Math.sqrt(2.0 * AppConstants.getGravity() * AppConstants.getGridStep() * 1.2);
    }

    public static int dist(ArrayList<Block> blocks, Block a, Block b) {

        if (abs(a.getX() - b.getX()) < 1) {
            // b
            // .  b is higher than a
            // .
            // a
            if (a.getY() > b.getY())
                return Integer.MAX_VALUE; // impossible to jump from a to b
            else {
                // a
                // ? check this blocks
                // ?
                // b
                for (Block block : blocks) {
                    if (block.getDegree() > 0 && block.getX() == b.getX() && block.getY() < b.getY() && block.getY() > a.getY()) {
                        return Integer.MAX_VALUE; // impossible to jump from a to b
                    }
                }
                return 0; // do nothing to jump from a to b
            }
        }

        // a  . . . . . . . . . . . b
        if (abs(a.getX() - b.getX()) > AppConstants.getGridStep()) {
            return Integer.MAX_VALUE; // impossible to jump from a to b (b is too far from a)
        }

        // a ?                      ? a
        // . ?  check this blocks   ? .
        // . ?                      ? .
        // . b                      b .
        if (b.getY() > a.getY()) {
            for (Block block : blocks) {
                if (block.getDegree() > 0 && block.getX() == b.getX()
                        && block.getY() < b.getY() && block.getY() > a.getY()) {
                    return Integer.MAX_VALUE; // impossible to jump from a to b
                }
            }
            if (a.getX() < b.getX())
                return 1; // one jump right
            else
                return -1; //one jump left
        }
        // ? .                          . ?
        // ? b                          b ?
        // ? check this blocks            ?
        // a                              a
        else if (b.getY() < a.getY() && b.getY() + AppConstants.getGridStep() >= a.getY()) {
            for (Block block : blocks) {
                if (block.getDegree() > 0 && block.getX() == a.getX()
                        && block.getY() < a.getY() && block.getY() > b.getY() /*- AppConstants.getGridStep()*/) {
                    return Integer.MAX_VALUE; // impossible to jump from a to b
                }
            }
            if (a.getX() < b.getX())
                return 1; // one jump right
            else
                return -1; //one jump left
        }
        else if (abs(b.getY() - a.getY()) < 1) {

            // a . b      b . a
            if (a.getX() < b.getX())
                return 1; // one jump right
            else
                return -1; //one jump left
        }
        return Integer.MAX_VALUE; // impossible to jump from a to b
    }


    public BlockType getType() {
        return type;
    }

    public int getDegree() {
        return degree;
    }

    public void decreaseDegree() { degree--; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public double getInitVelocity() {
        return initVelocity;
    }
}


