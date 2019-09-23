package edu.amd.spbstu.jumper;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;


public class AppConstants {
    private static GameEngine gameEngine;
    private static BitmapBank bitmapBank;
    private static SoundPlayer soundPlayer;
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    private static double gravity = 3.0;
    private static double gridStep;
    private static int playerH;
    private static int playerW;
    private static int blockW;
    private static int blockH;

    private static int currLevel;

    public static int pauseX, pauseY, pauseH, pauseW;
    public static int restartX, restartY, restartH, restartW;
    public static int soundX, soundY, soundH, soundW;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void initialization(Context context) {
        setScreenSize(context);
        if (soundPlayer == null) {
            soundPlayer = new SoundPlayer(context);
        }

        if (bitmapBank == null) {
            bitmapBank = new BitmapBank(context.getResources());
        }

        if (gameEngine == null) {
            gameEngine = new GameEngine();
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

    public static double getGridStep() {
        return gridStep;
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

    public static void setGridStep(double gridStep) {
        AppConstants.gridStep = gridStep;
    }

    public static void setCurrLevel(int currLevel) {
        AppConstants.currLevel = currLevel;
        AppConstants.getGameEngine().restartGame();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void setScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        SCREEN_HEIGHT = metrics.widthPixels; // no mistake here
        SCREEN_WIDTH = metrics.heightPixels;
    }

}
