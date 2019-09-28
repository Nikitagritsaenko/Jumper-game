package edu.amd.spbstu.jumper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import static java.lang.Math.min;
import static java.lang.Math.round;

public class BitmapBank {
    Resources resources;

    private Bitmap background;
    private Bitmap player;
    private Bitmap[] blocks;
    private Bitmap pause;
    private Bitmap restart;
    private Bitmap play;
    private Bitmap exit;
    private Bitmap soundOn;
    private Bitmap soundOff;
    private Bitmap b, b_special;

    private double step;
    private int numBlocksX;
    private int numBlocksY;

    private int numBlocks;

    private Bitmap squeezeImage(Bitmap bitmap, int squeeze) {
        int h = bitmap.getHeight();
        int w = bitmap.getWidth();
        return Bitmap.createScaledBitmap(bitmap, w / squeeze, h / squeeze, false);
    }

    private Bitmap setImageSize(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private Bitmap scaleImage(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)(AppConstants.getScreenWidth()), (int)(AppConstants.getScreenHeight()), false);
    }

    public void scalePlayer(double scale) {
        player = BitmapFactory.decodeResource(resources, R.drawable.penguine);
        player = Bitmap.createScaledBitmap(player, (int)scale, (int)scale, false);
        AppConstants.setPlayerW(player.getWidth());
        AppConstants.setPlayerH(player.getHeight());
    }

    public void initBlocks(){
        if (blocks == null) {
            blocks = new Bitmap[numBlocks];

            int startIdx = AppConstants.getGameEngine().getStartIdx();
            int endIdx   = AppConstants.getGameEngine().getEndIdx();

            blocks[0] = b_special;
            blocks[0] = setImageSize(blocks[0], player.getWidth(), player.getHeight());

            int j = 1;
            for (int i = 0; i < numBlocks; i++) {
                if (i != startIdx && i != endIdx) {
                    blocks[j] = b;
                    blocks[j] = setImageSize(blocks[j], player.getWidth(), player.getHeight());
                    j++;
                }
            }


            blocks[numBlocks - 1] = b_special;
            blocks[numBlocks - 1] = setImageSize(blocks[numBlocks - 1], player.getWidth(), player.getHeight());

            AppConstants.setBlockH(blocks[0].getHeight());
            AppConstants.setBlockW(blocks[0].getWidth());
        }
    }

    public BitmapBank(Resources res) {
        resources = res;

        pause = BitmapFactory.decodeResource(res, R.drawable.pause);
        restart = BitmapFactory.decodeResource(res, R.drawable.restart);
        restart = setImageSize(restart, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        play = BitmapFactory.decodeResource(res, R.drawable.play);
        play = setImageSize(play, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOn = BitmapFactory.decodeResource(res, R.drawable.audio);
        soundOn = setImageSize(soundOn, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOff = BitmapFactory.decodeResource(res, R.drawable.no_audio);
        soundOff = setImageSize(soundOff, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        exit = BitmapFactory.decodeResource(res, R.drawable.exit);
        exit = setImageSize(exit, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));

        background = BitmapFactory.decodeResource(res, R.drawable.background2);
        background = scaleImage(background);
        player = BitmapFactory.decodeResource(res, R.drawable.penguine);
        b = BitmapFactory.decodeResource(res, R.drawable.ice_cube);
        b_special = BitmapFactory.decodeResource(res, R.drawable.ice_cube_special);

        AppConstants.exitH = exit.getHeight();
        AppConstants.exitW = exit.getWidth();
        AppConstants.exitX = AppConstants.getScreenWidth() - 2 * pause.getWidth();
        AppConstants.exitY = 0;

        AppConstants.pauseH = pause.getHeight();
        AppConstants.pauseW = pause.getWidth();
        AppConstants.pauseX = AppConstants.getScreenWidth() - 3 * pause.getWidth();
        AppConstants.pauseY = 0;

        AppConstants.restartH = restart.getHeight();
        AppConstants.restartW = restart.getWidth();
        AppConstants.restartX = AppConstants.pauseX - restart.getWidth();
        AppConstants.restartY = 0;

        AppConstants.soundH = soundOn.getHeight();
        AppConstants.soundW = soundOn.getWidth();
        AppConstants.soundX = AppConstants.restartX - soundOn.getWidth();
        AppConstants.soundY = 0;


    }

    public void flipPlayer() {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        player = Bitmap.createBitmap(player, 0, 0, player.getWidth(), player.getHeight(), matrix, true);
    }

    public Bitmap getBackground() {
        return background;
    }

    public Bitmap[] getBlocks() { return blocks; }

    public void setBlocks(Bitmap[] blocks) {
        this.blocks = blocks;
    }

    public Bitmap getPlayer() {
        return player;
    }

    public Bitmap getPause() {
        return pause;
    }

    public Bitmap getRestart() {
        return restart;
    }

    public Bitmap getExit() {
        return exit;
    }

    public Bitmap getPlay() {
        return play;
    }

    public Bitmap getSoundOn() {
        return soundOn;
    }

    public Bitmap getSoundOff() {
        return soundOff;
    }

    public int getBlocksNum() {
        return numBlocks;
    }

    public void setBlocksNum(int num) {
        numBlocks = num;
    }

    public double getStep() {
        return step;
    }

    public int getNumBlocksX() {
        return numBlocksX;
    }

    public int getNumBlocksY() {
        return numBlocksY;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public int getBackgroundWidth() {
        return background.getWidth();
    }

    public int getBackgroundHeight() {
        return background.getHeight();
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void setNumBlocksX(int numBlocksX) {
        this.numBlocksX = numBlocksX;
    }

    public void setNumBlocksY(int numBlocksY) {
        this.numBlocksY = numBlocksY;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }
}

