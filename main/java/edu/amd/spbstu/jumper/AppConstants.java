package edu.amd.spbstu.jumper;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class AppConstants {
    private static GameEngine gameEngine;
    private static BitmapBank bitmapBank;
    private static SoundPlayer soundPlayer;
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    private static int numLevels = 40;

    private static double gravity;
    private static int gridStepX, gridStepY;
    private static int playerH;
    private static int playerW;
    private static int blockW;
    private static int blockH;
    private static int currLevel;

    public static int pauseX, pauseY, pauseH, pauseW;
    public static int restartX, restartY, restartH, restartW;
    public static int soundX, soundY, soundH, soundW;
    public static int exitX, exitY, exitH, exitW;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void initialization(Context context) {
        gravity = SCREEN_HEIGHT / 350;

        if (soundPlayer == null) {
            soundPlayer = new SoundPlayer(context);
        }

        if (bitmapBank == null) {
            bitmapBank = new BitmapBank(context.getResources());
        }

        if (gameEngine == null) {
            gameEngine = new GameEngine(context);
        }
    }

    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    public static double getGravity() {
        return gravity;
    }

    public static SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getGridStepX() {
        return gridStepX;
    }

    public static int getGridStepY() {
        return gridStepY;
    }

    public static int getPlayerH() {
        return playerH;
    }

    public static int getPlayerW() {
        return playerW;
    }

    public static int getBlockW() {
        return blockW;
    }

    public static int getBlockH() {
        return blockH;
    }

    public static int getNumLevels() {
        return numLevels;
    }

    public static void setPlayerH(int playerH) {
        AppConstants.playerH = playerH;
    }

    public static void setPlayerW(int playerW) {
        AppConstants.playerW = playerW;
    }

    public static void setBlockW(int blockW) {
        AppConstants.blockW = blockW;
    }

    public static void setBlockH(int blockH) {
        AppConstants.blockH = blockH;
    }

    public static int getCurrLevel() {
        return currLevel;
    }

    public static void setGridStepX(int gridStepX) {
        AppConstants.gridStepX = gridStepX;
    }

    public static void setGridStepY(int gridStepY) {
        AppConstants.gridStepY = gridStepY;
    }

    public static void setCurrLevel(int currLevel) {

        if (currLevel > numLevels)
            return;
        AppConstants.currLevel = currLevel;
        AppConstants.getBitmapBank().setBlocks(null);
        AppConstants.getGameEngine().restartGame();
    }


    public static void setScreenWidth(int screenWidth) {
        SCREEN_WIDTH = screenWidth;
    }

    public static void setScreenHeight(int screenHeight) {
        SCREEN_HEIGHT = screenHeight;
    }

    public static int getExitX() {
        return exitX;
    }

    public static int getExitY() {
        return exitY;
    }

    public static int getExitH() {
        return exitH;
    }

    public static int getExitW() {
        return exitW;
    }
}
