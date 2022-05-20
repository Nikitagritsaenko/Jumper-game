package grits.jumper;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class AppConstants {
    static final double SQUEEZE_BLOCK_COEFFICIENT = 0.8;
    static final double GRAVITY_SCALE_COEFFICIENT = 70.0;
    static final int PORTAL_DISCOLORATION_SPEED = 20;
    static final int INF_PORTAL_DISCOLORATION_SPEED = 5;
    static final int BLOCK_DISCOLORATION_SPEED = 50;

    private static double gravity;

    private static final int NUM_LEVELS = 56;

    private static GameEngine gameEngine;
    private static BitmapBank bitmapBank;
    private static SoundPlayer soundPlayer;

    private static int currLevel;
    
    private static int screenWidth, screenHeight;
    private static int gridStepX, gridStepY;
    private static int playerH, playerW;
    private static int blockW, blockH;

    static int pauseX, pauseY, pauseH, pauseW;
    static int restartX, restartY, restartH, restartW;
    static int soundX, soundY, soundH, soundW;
    static int exitX, exitY, exitH, exitW;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    static void initialization(Context context) {
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

    static void setCurrLevel(int currLevel) {
        AppConstants.currLevel = Math.min(currLevel, NUM_LEVELS + 1); // current lvl or endgame bonus
        AppConstants.getBitmapBank().setBlocks(null);
        AppConstants.getGameEngine().restartGame();
    }
    
    static GameEngine getGameEngine() {
        return gameEngine;
    }

    static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    static double getGravity() {
        return gravity;
    }

    static SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    static int getScreenWidth() {
        return screenWidth;
    }

    static int getScreenHeight() {
        return screenHeight;
    }

    static int getGridStepX() {
        return gridStepX;
    }

    static int getGridStepY() {
        return gridStepY;
    }

    static int getPlayerH() {
        return playerH;
    }

    static int getPlayerW() {
        return playerW;
    }

    static int getBlockW() {
        return blockW;
    }

    static int getBlockH() {
        return blockH;
    }

    static int getNumLevels() {
        return NUM_LEVELS;
    }

    static int getExitX() {
        return exitX;
    }

    static int getExitY() {
        return exitY;
    }

    static int getExitH() {
        return exitH;
    }

    static int getExitW() {
        return exitW;
    }

    static int getCurrLevel() {
        return currLevel;
    }

    static void setPlayerH(int playerH) {
        AppConstants.playerH = playerH;
    }

    static void setPlayerW(int playerW) {
        AppConstants.playerW = playerW;
    }

    static void setBlockW(int blockW) {
        AppConstants.blockW = blockW;
    }

    static void setBlockH(int blockH) {
        AppConstants.blockH = blockH;
    }

    static void setGridStepX(int gridStepX) {
        AppConstants.gridStepX = gridStepX;
    }

    static void setGridStepY(int gridStepY) {
        AppConstants.gridStepY = gridStepY;
    }

    static void setScreenWidth(int screenWidth) {
        AppConstants.screenWidth = screenWidth;
    }

    static void setScreenHeight(int screenHeight) {
        AppConstants.screenHeight = screenHeight;
    }

    static void setGravity(double gravity) {
        AppConstants.gravity = gravity;
    }

    static void invertGravity() {
        gravity = -gravity;
    }
}
