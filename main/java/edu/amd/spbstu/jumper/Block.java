package edu.amd.spbstu.jumper;

import java.util.ArrayList;

import static java.lang.StrictMath.abs;

public class Block {
    private BlockType type;
    private int degree;
    private int x, y, coordX, coordY;
    private int idx;
    private int pos;
    private int alpha = 255;
    private double initVelocity;

    public Block(BlockType type, int x, int y) {
        this.type = type;

        if (type == BlockType.EMPTY)
            degree = 0;
        else if (type == BlockType.DESTROYABLE_1 || type == BlockType.SPRING || type == BlockType.PORTAL)
            degree = 1;
        else if (type == BlockType.DESTROYABLE_2)
            degree = 2;
        else
            degree = Integer.MAX_VALUE;

        this.x = x;
        this.y = y;
        this.coordX = x;
        this.coordY = y;
        if (type == BlockType.SPRING)
            this.initVelocity = Math.sqrt(2.0 * AppConstants.getGravity() * AppConstants.getGridStepY() * 2.3);
        else
            this.initVelocity = Math.sqrt(2.0 * AppConstants.getGravity() * AppConstants.getGridStepY() * 1.3);

    }

    public static int dist(ArrayList<Block> blocks, Block a, Block b) {
        if (b.getType() == BlockType.START) {
            return Integer.MAX_VALUE;
        }

        if (a.getType() == BlockType.END) {
            return Integer.MAX_VALUE;
        }

        if (a.getX() == b.getX() && a.getY() == b.getY()) {
            return Integer.MAX_VALUE;
        }

        if (a.getX() == b.getX()) {
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
                if (a.getDegree() == 2) {
                    return Integer.MAX_VALUE;
                }
                if (a.getType() == BlockType.START) {
                    return Integer.MAX_VALUE;
                }
                for (Block block : blocks) {
                    if (block.getDegree() > 0 && block.getX() == b.getX()
                            && b.getY() > block.getY() && block.getY() > a.getY()) {
                        return Integer.MAX_VALUE; // impossible to jump from a to b
                    }
                }
                return 0; // do nothing to jump from a to b
            }
        }

        // a  . . . . . . . . . . . b       b  . . . . . . . . . . . a
        if (abs(a.getX() - b.getX()) > AppConstants.getGridStepX()) {
            return Integer.MAX_VALUE; // impossible to jump from a to b (b is too far from a)
        }

        if (a.getY() - b.getY() > AppConstants.getGridStepX()) {
            return Integer.MAX_VALUE; // impossible to jump from a to b (b is too far from a)
        }

        // . .                      . .
        // a ?                      ? a
        // . ?  check this blocks   ? .
        // . ?                      ? .
        // . b                      b .
        if (b.getY() > a.getY()) {
            for (Block block : blocks) {
                /*if (block.getX() == a.getX() && block.getDegree() > 0
                        && a.getY() - block.getY() == AppConstants.getGridStep()) {
                    return Integer.MAX_VALUE;
                }*/
                if (block.getDegree() > 0 && block.getX() == b.getX()
                        && b.getY() > block.getY() && block.getY() + AppConstants.getGridStepY() > a.getY()) {
                    return Integer.MAX_VALUE; // impossible to jump from a to b
                }
            }
            if (a.getX() < b.getX())
                return 1; // one jump right
            else
                return -1; //one jump left
        }

        // . ?                          ? .
        // ? b                          b ?
        // a check this blocks            a
        else if (b.getY() < a.getY()) {
            for (Block block : blocks) {
                /*if (block.getDegree() > 0 && block.getX() == b.getX() &&
                        b.getY() - block.getY() == AppConstants.getGridStep()) {
                    return Integer.MAX_VALUE;
                }*/
                if (block.getDegree() > 0 && block.getX() == a.getX()
                        && /*a.getY() > block.getY() &&*/ a.getY() - block.getY() == AppConstants.getGridStepY()) {
                    return Integer.MAX_VALUE; // impossible to jump from a to b
                }
            }
            if (b.getX() > a.getX())
                return 1; // one jump right
            else
                return -1; //one jump left
        }
        else {
            if (b.getX() > a.getX())
                return 1; // one jump right
            else
                return -1; //one jump left
        }
        //return Integer.MAX_VALUE; // impossible to jump from a to b
    }


    public BlockType getType() {
        return type;
    }

    public int getDegree() {
        return degree;
    }

    public void decreaseDegree() { degree--; }

    public void increaseDegree() { degree++; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIdx() {
        return idx;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public double getInitVelocity() {
        return initVelocity;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}


