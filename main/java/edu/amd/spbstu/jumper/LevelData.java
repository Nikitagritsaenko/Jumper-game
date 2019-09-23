package edu.amd.spbstu.jumper;

public class LevelData {
    private int lvlIdx;
    private int numX;
    private int numY;
    private int[] indices;

    public LevelData(int lvlIdx, int numX, int numY, int[] indices) {
        this.lvlIdx = lvlIdx;
        this.numX = numX;
        this.numY = numY;
        this.indices = indices;
    }

    public int getLvlIdx() {
        return lvlIdx;
    }

    public int getNumX() {
        return numX;
    }

    public int getNumY() {
        return numY;
    }

    public int[] getIndices() {
        return indices;
    }


}
