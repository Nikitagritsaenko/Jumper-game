package edu.amd.spbstu.jumper;

import android.util.Log;

public class Player {
    private double X;
    private double Y;
    private double velocity;

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
        double newX = this.X + AppConstants.gridStep;
        if (newX > AppConstants.SCREEN_WIDTH - AppConstants.playerW) {
            this.X = AppConstants.SCREEN_WIDTH - AppConstants.playerW;
        }
        else {
            this.X = newX;
        }
    }

    public void moveLeft() {
        double newX = this.X - AppConstants.gridStep;
        if (newX < 0) {
            this.X = 0;
        }
        else {
            this.X = newX;
        }
    }

}
