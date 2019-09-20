package edu.amd.spbstu.jumper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapBank {

    private Bitmap background;
    private Bitmap player;
    private Bitmap[] blocks;
    private Bitmap pause;
    private Bitmap restart;
    private Bitmap play;
    private Bitmap soundOn;
    private Bitmap soundOff;

    private int blocks_num = LevelGenerator.getBlockNum(AppConstants.getCurrLevel());


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

    public BitmapBank(Resources res) {
        pause = BitmapFactory.decodeResource(res, R.drawable.pause);
        restart = BitmapFactory.decodeResource(res, R.drawable.restart);
        restart = setImageSize(restart, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        play = BitmapFactory.decodeResource(res, R.drawable.play);
        play = setImageSize(play, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOn = BitmapFactory.decodeResource(res, R.drawable.audio);
        soundOn = setImageSize(soundOn, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOff = BitmapFactory.decodeResource(res, R.drawable.no_audio);
        soundOff = setImageSize(soundOff, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));

        background = BitmapFactory.decodeResource(res, R.drawable.background2);
        background = scaleImage(background);
        player = BitmapFactory.decodeResource(res, R.drawable.penguine);

        player = squeezeImage(player, 2);

        blocks = new Bitmap[blocks_num];
        for (int i = 0; i < blocks_num; i++) {
            blocks[i] = BitmapFactory.decodeResource(res, R.drawable.ice_cube);
            blocks[i] = setImageSize(blocks[i], player.getWidth(), player.getHeight());
        }
        AppConstants.setBlockH(blocks[0].getHeight());
        AppConstants.setBlockW(blocks[0].getWidth());
        AppConstants.setPlayerW(player.getWidth());
        AppConstants.setPlayerH(player.getHeight());

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

    public int getBlocksNum() { return blocks_num; }

    public Bitmap[] getBlocks() { return blocks; }

    public Bitmap getPlayer() {
        return player;
    }

    public Bitmap getPause() {
        return pause;
    }

    public Bitmap getRestart() {
        return restart;
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

    public int getBlocks_num() {
        return blocks_num;
    }

    public int getBackgroundWidth() {
        return background.getWidth();
    }

    public int getBackgroundHeight() {
        return background.getHeight();
    }
}

