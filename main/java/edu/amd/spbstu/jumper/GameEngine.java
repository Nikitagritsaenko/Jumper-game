package edu.amd.spbstu.jumper;

import android.graphics.Canvas;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static edu.amd.spbstu.jumper.LevelGenerator.generateBlocks;
import static java.lang.Math.random;


public class GameEngine {
    BackgroundImage backgroundImage;
    Player player = new Player();
    int level_num = 1;
    ArrayList<Block> blocks;

    GameStates gameState = GameStates.NOT_STARTED;

    public GameEngine() {
        blocks = generateBlocks(level_num);
        backgroundImage = new BackgroundImage();
    }

    public void updateAndDrawBackgroundImage(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX(), backgroundImage.getY(), null);
    }

    public void updateBlocks(Canvas canvas) {
        int len = AppConstants.getBitmapBank().getBlocksNum();
        for (int i = 0; i < len; i++) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBlocks()[i], blocks.get(i).getX(), blocks.get(i).getY(), null);
        }
    }

    public void updateAndDrawPlayer(Canvas canvas) {
        if (gameState == GameStates.PLAYING) {
            if (player.getY() < (AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getHeight()) || player.getVelocity() < 0) {
                player.setVelocity(player.getVelocity() + AppConstants.gravity);
                player.setY(player.getY() + player.getVelocity());
            }
        }

        canvas.drawBitmap(AppConstants.getBitmapBank().getPlayer(), player.getX(), player.getY(), null);
    }
}
