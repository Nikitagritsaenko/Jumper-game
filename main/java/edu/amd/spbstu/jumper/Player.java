package edu.amd.spbstu.jumper;

import android.util.Log;

public class Player {
    private double X;
    private double Y;
    private double velocity;
    private boolean isFlipped = false;

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Player() {
        velocity = 0;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setX(double X) {
        this.X = X;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

    public void moveRight() {
        if (isFlipped == true) {
            AppConstants.getBitmapBank().flipPlayer();
            isFlipped = false;
        }
        GameEngine.setMovingRight(true);

    }

    public void moveLeft() {
        if (isFlipped == false) {
            AppConstants.getBitmapBank().flipPlayer();
            isFlipped = true;
        }
        GameEngine.setMovingLeft(true);
    }


    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }
}
