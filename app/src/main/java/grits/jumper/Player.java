package grits.jumper;

class Player {
    private double X;
    private double Y;
    private double velocity;
    private boolean isFlipped = false;
    private boolean isLeftRightDirectionSwapped = false;

    double getVelocity() {
        return velocity;
    }

    void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    Player() {
        velocity = 0;
    }

    double getX() {
        return X;
    }

    double getY() {
        return Y;
    }

    void setX(double X) {
        this.X = X;
    }

    void setY(double Y) {
        this.Y = Y;
    }

    void moveRight() {
        if (AppConstants.getGameEngine().getGameState() == GameState.PAUSED) {
            return;
        }
        if (isFlipped) {
            AppConstants.getBitmapBank().flipPlayer();
            isFlipped = false;
        }
        GameEngine.setMovingRight(true);
    }

    void moveLeft() {
        if (AppConstants.getGameEngine().getGameState() == GameState.PAUSED) {
            return;
        }
        if (!isFlipped) {
            AppConstants.getBitmapBank().flipPlayer();
            isFlipped = true;
        }
        GameEngine.setMovingLeft(true);
    }


    void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    boolean isFlipped() {
        return isFlipped;
    }

    boolean isLeftRightDirectionSwapped() {
        return isLeftRightDirectionSwapped;
    }

    public void setLeftRightDirectionSwapped(boolean isSwapped) {
        isLeftRightDirectionSwapped = isSwapped;
    }

    public void swapDirection() {
        isLeftRightDirectionSwapped = !isLeftRightDirectionSwapped;
    }
}
