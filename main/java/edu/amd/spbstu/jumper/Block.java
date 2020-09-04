package grits.jumper;

public class Block {

    private BlockType type;
    private int degree;
    private int coordinateX, coordinateY;
    private int idx;
    private int pos;
    private int alpha = 255;
    private double initVelocity;

    Block(BlockType type, int x, int y) {

        this.type = type;

        if (type == BlockType.EMPTY) {
            degree = 0;
        }
        else if (type == BlockType.DESTROYABLE_1 || type == BlockType.SPRING || type == BlockType.PORTAL) {
            degree = 1;
        }
        else if (type == BlockType.DESTROYABLE_2) {
            degree = 2;
        }
        else {
            degree = Integer.MAX_VALUE;
        }

        this.coordinateX = x;
        this.coordinateY = y;

        if (type == BlockType.SPRING) {
            this.initVelocity = Math.sqrt(2.0 * AppConstants.getGravity() * AppConstants.getGridStepY() * 2.3);
        }
        else {
            this.initVelocity = Math.sqrt(2.0 * AppConstants.getGravity() * AppConstants.getGridStepY() * 1.3);
        }

    }

    BlockType getType() {
        return type;
    }

    int getDegree() {
        return degree;
    }

    public void decreaseDegree() { degree--; }

    void increaseDegree() { degree++; }

    int getIdx() {
        return idx;
    }

    int getX() {
        return coordinateX;
    }

    int getY() {
        return coordinateY;
    }

    void setX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    void setIdx(int idx) {
        this.idx = idx;
    }

    double getInitVelocity() {
        return initVelocity;
    }

    int getPos() {
        return pos;
    }

    void setPos(int pos) {
        this.pos = pos;
    }

    int getAlpha() {
        return alpha;
    }

    void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}


