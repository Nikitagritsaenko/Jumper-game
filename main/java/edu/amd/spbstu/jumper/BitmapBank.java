package edu.amd.spbstu.jumper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RelativeLayout;

public class BitmapBank {

    private Bitmap background;
    private Bitmap player;
    private Bitmap[] blocks;
    private int blocks_num = 3;



    public Bitmap scaleImage(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)(AppConstants.SCREEN_HEIGHT), (int)(AppConstants.SCREEN_WIDTH), false);
    }

    public BitmapBank(Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.background2);
        background = scaleImage(background);
        player = BitmapFactory.decodeResource(res, R.drawable.penguine);
        blocks = new Bitmap[blocks_num];
        for (int i = 0; i < blocks_num; i++) {
            blocks[i] = BitmapFactory.decodeResource(res, R.drawable.ice_cube_mini);
        }
    }

    public Bitmap getBackground() {
        return background;
    }

    public int getBlocksNum() { return blocks_num; }

    public int getWidth() {
        return background.getWidth();
    }

    public Bitmap[] getBlocks() { return blocks; }

    public int getHeight() {
        return background.getHeight();
    }

    public Bitmap getPlayer() {
        return player;
    }
}

