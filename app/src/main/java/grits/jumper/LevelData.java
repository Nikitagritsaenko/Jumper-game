package grits.jumper;

class LevelData {
    private int lvlIdx;
    private int numX;
    private int numY;
    private int startIdx, endIdx;
    private int[] indices;

    LevelData(int lvlIdx, int startIdx, int endIdx, int numX, int numY, int[] indices) {
        this.lvlIdx = lvlIdx;
        this.numX = numX;
        this.numY = numY;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.indices = indices;
    }

    int getNumX() {
        return numX;
    }

    int getNumY() {
        return numY;
    }

    int[] getIndices() {
        return indices;
    }

    int getStartIdx() {
        return startIdx;
    }

    int getEndIdx() {
        return endIdx;
    }
}
