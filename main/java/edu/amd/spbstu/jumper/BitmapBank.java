package edu.amd.spbstu.jumper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;


public class BitmapBank {
    Resources resources;

    private Bitmap background;
    private Bitmap player;
    private Bitmap[] blocks;
    private Boolean[] isBlockMinimized;
    private Bitmap pause;
    private Bitmap restart;
    private Bitmap play;
    private Bitmap exit;
    private Bitmap soundOn;
    private Bitmap soundOff;
    private Bitmap portal;
    public ArrayList<Integer> portalIdxs = new ArrayList<>();
    private Bitmap b, b_special, b_double, b_finish, b_spring;

    private int stepX, stepY;

    private int numBlocksX;
    private int numBlocksY;

    private int numBlocks;

    private double squeeze = 1.25;

    public void maximizeAllBlocks() {
        for (int i = 0; i < blocks.length; i++) {
            maximizeBlock(i);
        }
    }
    public void maximizeBlock(int i) {
        if (!isBlockMinimized[i])
            return;
        blocks[i] = Bitmap.createScaledBitmap(blocks[i], AppConstants.getBlockW(), AppConstants.getBlockH(), false);
        Block b = AppConstants.getGameEngine().getBlocks().get(i);
        b.setCoordX(b.getCoordX() - (int)(blocks[i].getWidth()  * (1.0 - 1 / squeeze) / 2));

        isBlockMinimized[i] = false;
    }

    public void minimizeBlock(int i) {
        if (isBlockMinimized[i])
            return;

        Block b = AppConstants.getGameEngine().getBlocks().get(i);
        b.setCoordX(b.getCoordX() + (int)(blocks[i].getWidth()    * (1.0 - 1 / squeeze) / 2));

        blocks[i] = Bitmap.createScaledBitmap(blocks[i], (int)(blocks[i].getWidth() / squeeze), (int)(blocks[i].getHeight() / squeeze), false);
        isBlockMinimized[i] = true;
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


    public void initBlocks(ArrayList<BlockType> block_types){
        if (blocks == null) {
            blocks = new Bitmap[numBlocks];

            int startIdx = AppConstants.getGameEngine().getStartIdx();
            int endIdx   = AppConstants.getGameEngine().getEndIdx();

            isBlockMinimized = new Boolean[numBlocks];
            for (int i = 0; i < numBlocks; i++)
                isBlockMinimized[i] = false;

            blocks[0] = b_special;
            blocks[0] = setImageSize(blocks[0], player.getWidth(), player.getHeight());

            int j = 1;
            for (int i = 0; i < numBlocks; i++) {
                if (i != startIdx && i != endIdx) {
                    if (block_types.get(j) == BlockType.DESTROYABLE_2)
                        blocks[j] = b_double;
                    else if (block_types.get(j) == BlockType.SPRING)
                        blocks[j] = b_spring;
                    else if (block_types.get(j) == BlockType.PORTAL) {
                        blocks[j] = portal;
                        portalIdxs.add(j);
                    }
                    else
                        blocks[j] = b;

                    boolean isPortal = false;
                    for (int idx: portalIdxs) {
                        if (idx == j) {
                            isPortal = true;
                        }
                    }

                    if (isPortal)
                        blocks[j] = setImageSize(blocks[j], player.getWidth() * 2, player.getHeight() * 2);
                    else
                        blocks[j] = setImageSize(blocks[j], player.getWidth(), player.getHeight());

                    j++;
                }
            }


            blocks[numBlocks - 1] = b_finish;
            blocks[numBlocks - 1] = setImageSize(blocks[numBlocks - 1], player.getWidth(), player.getHeight() * 2);

            AppConstants.setBlockH(blocks[0].getHeight());
            AppConstants.setBlockW(blocks[0].getWidth());

        }
        else
            AppConstants.getBitmapBank().maximizeAllBlocks();
    }

    public BitmapBank(Resources res) {
        resources = res;

        BitmapFactory.Options options_game_objects = new BitmapFactory.Options();
        options_game_objects.inSampleSize = 8;

        BitmapFactory.Options options_game_buttons = new BitmapFactory.Options();
        options_game_buttons.inSampleSize = 1;

        BitmapFactory.Options options_game_background = new BitmapFactory.Options();
        options_game_background.inSampleSize = 1;

        pause = BitmapFactory.decodeResource(res, R.drawable.pause, options_game_buttons);
        restart = BitmapFactory.decodeResource(res, R.drawable.restart, options_game_buttons);
        restart = setImageSize(restart, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        play = BitmapFactory.decodeResource(res, R.drawable.play, options_game_buttons);
        play = setImageSize(play, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOn = BitmapFactory.decodeResource(res, R.drawable.audio, options_game_buttons);
        soundOn = setImageSize(soundOn, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOff = BitmapFactory.decodeResource(res, R.drawable.no_audio, options_game_buttons);
        soundOff = setImageSize(soundOff, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        exit = BitmapFactory.decodeResource(res, R.drawable.exit, options_game_buttons);
        exit = setImageSize(exit, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));

        background = BitmapFactory.decodeResource(res, R.drawable.background2, options_game_background);
        background = scaleImage(background);
        player = BitmapFactory.decodeResource(res, R.drawable.penguine, options_game_objects);
        b = BitmapFactory.decodeResource(res, R.drawable.ice_cube, options_game_objects);
        b_special = BitmapFactory.decodeResource(res, R.drawable.ice_cube_special, options_game_objects);
        b_double = BitmapFactory.decodeResource(res, R.drawable.ice_cube_double, options_game_objects);
        b_finish = BitmapFactory.decodeResource(res, R.drawable.ice_cube_finish, options_game_objects);
        b_spring = BitmapFactory.decodeResource(res, R.drawable.ice_cube_spring, options_game_objects);

        portal = BitmapFactory.decodeResource(res, R.drawable.portal, options_game_buttons);

        AppConstants.exitH = exit.getHeight();
        AppConstants.exitW = exit.getWidth();
        AppConstants.exitX = AppConstants.getScreenWidth() - 4 * pause.getWidth();
        AppConstants.exitY = 0;

        AppConstants.pauseH = pause.getHeight();
        AppConstants.pauseW = pause.getWidth();
        AppConstants.pauseX = AppConstants.getScreenWidth() - 5 * pause.getWidth();
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

    public int getNumBlocksX() {
        return numBlocksX;
    }

    public int getNumBlocksY() {
        return numBlocksY;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }
    public void setStepY(int stepY) {
        this.stepY = stepY;
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

