package edu.amd.spbstu.jumper;

public class BackgroundImage {
    private int backgroundImageX, backgroundImageY;

    public BackgroundImage() {
        backgroundImageX = 0;
        backgroundImageY = 0;
    }

    public int getX() {
        return backgroundImageX;
    }

    public int getY() {
        return backgroundImageY;
    }


    public void setX(int backgroundImageX) {
        this.backgroundImageX = backgroundImageX;
    }

    public void setY(int backgroundImageY) {
        this.backgroundImageY = backgroundImageY;
    }

}
