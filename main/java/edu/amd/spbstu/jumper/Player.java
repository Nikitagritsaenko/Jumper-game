package edu.amd.spbstu.jumper;

public class Player {
    private float X;
    private float Y;
    private float velocity;


    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }


    public Player() {
        X = 300; ///
        Y = 300;
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }

    public void setX(float X) {
        this.X = X;
    }

    public void setY(float Y) {
        this.Y = Y;
    }
}
