package edu.amd.spbstu.jumper;

import android.graphics.Canvas;
import java.util.ArrayList;

import static edu.amd.spbstu.jumper.LevelGenerator.generateBlocks;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GameEngine {
    private BackgroundImage backgroundImage;
    private ArrayList<Block> blocks;
    private GameStates gameState = GameStates.NOT_STARTED;
    private static Player player = new Player();
    private SoundPlayer soundPlayer = AppConstants.getSoundPlayer();
    private static boolean isMovingLeft = false;
    private static boolean isMovingRight = false;
    private static boolean isSoundOn = true;
    private double totalMoved = 0.0;
    private double jumpStep = AppConstants.getGridStep() / 5.0;
    private static boolean hasTouchedBlock = false;

    public static void setMovingLeft(boolean movingLeft) {
        if (hasTouchedBlock) {
            isMovingLeft = movingLeft;
            hasTouchedBlock = false;
        }
    }

    public static void setMovingRight(boolean movingRight) {
        if (hasTouchedBlock) {
            isMovingRight = movingRight;
            hasTouchedBlock = false;
        }
    }

    public GameEngine() {
        blocks = generateBlocks(AppConstants.getCurrLevel());
        backgroundImage = new BackgroundImage();
        player.setX(blocks.get(0).getX());
        player.setY(blocks.get(0).getY() - (int)(AppConstants.getBlockH() * 0.8) - AppConstants.getGridStep());
        // check flip to run again correctly
        if (player.isFlipped())  {
            AppConstants.getBitmapBank().flipPlayer();
        }
        player.setFlipped(false);
        player.setVelocity(0.0);
        isMovingLeft = false;
        isMovingRight = false;
        totalMoved = 0.0;
        hasTouchedBlock = false;
        gameState = GameStates.PLAYING;
    }

    public void updateAndDrawBackgroundImage(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX(), backgroundImage.getY(), null);
        canvas.drawBitmap(AppConstants.getBitmapBank().getRestart(), AppConstants.restartX, AppConstants.restartY, null);
        if (gameState != GameStates.PAUSED)
            canvas.drawBitmap(AppConstants.getBitmapBank().getPause(), AppConstants.pauseX, AppConstants.pauseY, null);
        else
            canvas.drawBitmap(AppConstants.getBitmapBank().getPlay(), AppConstants.pauseX, AppConstants.pauseY, null);
        if (isSoundOn)
            canvas.drawBitmap(AppConstants.getBitmapBank().getSoundOn(), AppConstants.soundX, AppConstants.soundY, null);
        else
            canvas.drawBitmap(AppConstants.getBitmapBank().getSoundOff(), AppConstants.soundX, AppConstants.soundY, null);
    }

    public void updateBlocks(Canvas canvas) {
        int len = AppConstants.getBitmapBank().getBlocksNum();
        for (int i = 0; i < len; i++) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBlocks()[i], blocks.get(i).getX(), blocks.get(i).getY(), null);
        }
    }

    public void updateAndDrawPlayer(Canvas canvas) {
        if (gameState != GameStates.PAUSED) {
            player.setVelocity(player.getVelocity() + AppConstants.getGravity());
            player.setY(player.getY() + player.getVelocity());
            if (gameState != GameStates.GAMEOVER) {
                if (isMovingRight) {
                    if (totalMoved >= AppConstants.getGridStep() / jumpStep) {
                        isMovingRight = false;
                        totalMoved = 0.0;
                    } else {
                        player.setX(player.getX() + jumpStep);
                        totalMoved++;
                    }
                }

                if (isMovingLeft) {
                    if (totalMoved >= AppConstants.getGridStep() / jumpStep) {
                        isMovingLeft = false;
                        totalMoved = 0.0;
                    } else {
                        player.setX(player.getX() - jumpStep);
                        totalMoved++;
                    }
                }
            }
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getPlayer(), (int)player.getX(), (int)player.getY(), null);
    }

    public void updateCollision() {
        if (gameState == GameStates.GAMEOVER) {
            if (player.getY() > AppConstants.getScreenHeight()) {
                restartGame();
            }
            return;
        }

        if (gameState == GameStates.PAUSED) {
            return;
        }

        double x = player.getX();
        double y = player.getY();

        boolean isFallingDown = true;

        for (Block b: blocks) {
            double sy = b.getY() - y;
            double sx = b.getX() - x;
            double s = sqrt(sx * sx + sy * sy); // distance between player's top left and block top left

            if (sx == 0 && sy > 0) {
                // here is a block to fall down
                isFallingDown = false;
            }

            if (sx > 0  && s < AppConstants.getPlayerW() && abs(sy) < 0.8 * AppConstants.getBlockH()) {
                isMovingRight = false;
                totalMoved = 0.0;
                gameState = GameStates.GAMEOVER;
            }
            else if (sx < 0  && s < AppConstants.getPlayerW() && abs(sy) < 0.8 * AppConstants.getBlockH()) {
                isMovingLeft = false;
                totalMoved = 0.0;
                gameState = GameStates.GAMEOVER;
            }
            else if (sy > 0  && s < 0.8 * AppConstants.getPlayerH()) {
                player.setVelocity(-b.getInitVelocity());
                player.setY(player.getY() + player.getVelocity());
                soundPlayer.playJumpSound();
                hasTouchedBlock = true;
            }
        }

        if (isFallingDown && !isMovingRight && !isMovingLeft) {
            soundPlayer.playFallingSound();
            gameState = GameStates.GAMEOVER;
        }
    }

    public void restartGame() {
        soundPlayer.resumeMenuSound();
        gameState = GameStates.PLAYING;
        blocks = generateBlocks(AppConstants.getCurrLevel());
        backgroundImage = new BackgroundImage();
        player.setX(blocks.get(0).getX());
        player.setY(blocks.get(0).getY() - (int)(AppConstants.getBlockH() * 0.8) - AppConstants.getGridStep());
        // check flip to run again correctly
        if (player.isFlipped())  {
            AppConstants.getBitmapBank().flipPlayer();
        }
        player.setFlipped(false);
        player.setVelocity(0.0);
        isMovingLeft = false;
        isMovingRight = false;
        totalMoved = 0.0;
        hasTouchedBlock = false;
    }

    public void pauseGame() {
        soundPlayer.pauseMenuSound();
        gameState = GameStates.PAUSED;
    }

    public void resumeGame() {
        soundPlayer.resumeMenuSound();
        gameState = GameStates.PLAYING;
    }

    public Player getPlayer() {
        return player;
    }

    public GameStates getGameState() {
        return gameState;
    }

    public boolean IsSoundOn() {
        return isSoundOn;
    }

    public void setSoundOn(boolean isSoundOn) {
        GameEngine.isSoundOn = isSoundOn;
    }
}
