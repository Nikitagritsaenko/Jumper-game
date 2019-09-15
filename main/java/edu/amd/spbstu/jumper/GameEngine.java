package edu.amd.spbstu.jumper;

import android.graphics.Canvas;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static edu.amd.spbstu.jumper.LevelGenerator.generateBlocks;
import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;

public class GameEngine {
    private BackgroundImage backgroundImage;
    int level_num = 1;
    private ArrayList<Block> blocks;
    private GameStates gameState = GameStates.NOT_STARTED;
    public Player player;


    public GameEngine() {
        blocks = generateBlocks(level_num);
        backgroundImage = new BackgroundImage();
        player = new Player();
        player.setX(blocks.get(0).getX());
        player.setY(blocks.get(0).getY() - (int)(AppConstants.blockH * 0.8) - 100);
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
        player.setVelocity(player.getVelocity() + AppConstants.gravity);
        player.setY(player.getY() + player.getVelocity());
        canvas.drawBitmap(AppConstants.getBitmapBank().getPlayer(), (int)player.getX(), (int)player.getY(), null);
    }

    public void updateCollision() {
        double x = player.getX();
        double y = player.getY();
        for (Block b: blocks) {
            double sy = b.getY() - y;
            double sx = b.getX() - x;
            double s = sqrt(sx * sx + sy * sy); // distance between player's top left and block top left

            if (sy > 0  && s < 0.8 * AppConstants.playerH) {
                player.setVelocity(-b.getInitVelocity());
                player.setY(player.getY() + player.getVelocity());
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
}
