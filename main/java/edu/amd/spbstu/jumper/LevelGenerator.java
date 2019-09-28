package edu.amd.spbstu.jumper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Math.min;


public class LevelGenerator {
    private ArrayList<LevelData> data = new ArrayList<>();
    private int sizeX, sizeY;
    private int startIdx, endIdx;
    private static Integer[] x;
    private static Integer[] y;

    private void setBitmapParams() {
        AppConstants.getBitmapBank().setNumBlocksX(sizeX);
        AppConstants.getBitmapBank().setNumBlocksY(sizeY);
        AppConstants.getBitmapBank().setNumBlocks(sizeY * sizeX);

        double stepX = AppConstants.getScreenWidth() / (sizeX + 1);
        double stepY = AppConstants.getScreenHeight() / (sizeY + 1);
        int step = min((int)stepX, (int)stepY);

        AppConstants.getBitmapBank().setStep(step);
        AppConstants.setGridStep(step);

        AppConstants.getBitmapBank().scalePlayer(step / 2.0);
        AppConstants.getGameEngine().getPlayer().setFlipped(false);
        AppConstants.getBitmapBank().initBlocks();

    }

    public void initLevels() {
        if (data.size() != 0)
            return;
        // ALL LEVELS DATA SETS HERE ------------------------------------------------------------------------------------------------
        data.add(new LevelData(1, 0, 13, 7, 2, new int[]{1, 0, 0, 1, 0, 1, 0,
                                                                                         0, 1, 1, 0, 1, 0, 1}));

        data.add(new LevelData(2, 10, 24, 7, 4, new int[]
                {0, 0, 0, 0, 1, 0, 0,
                0, 0, 0, 1, 0, 1, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1}));


        data.add(new LevelData(3, 0, 35, 9, 4, new int[]{1, 0, 0, 1, 0, 1, 0, 0, 0,
                                                                                         0, 0, 1, 0, 1, 0, 0, 0, 1,
                                                                                         0, 1, 0, 0, 0, 1, 0, 1, 0,
                                                                                         0, 0, 0, 0, 0, 1, 1, 0, 1}));

        data.add(new LevelData(4, 0, 23, 8, 3, new int[]
                {1, 1, 0, 0, 1, 0, 0, 1,
                0, 0, 1, 1, 1, 0, 1, 1,
                0, 1, 0, 0, 1, 1, 0, 1}));


        data.add(new LevelData(5, 0, 21, 7, 4, new int[]
                {1, 0, 0, 0, 0, 1, 0,
                0, 1, 0, 1, 1, 0, 1,
                0, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 0, 0, 0}));


        // --------------------------------------------------------------------------------------------------------------------------
    }

    public int[] getBlockIdxs(int level) {
        LevelData ld = data.get(level-1);
        sizeX = ld.getNumX();
        sizeY = ld.getNumY();
        startIdx = ld.getStartIdx();
        endIdx = ld.getEndIdx();

        AppConstants.getGameEngine().setStartIdx(startIdx);
        AppConstants.getGameEngine().setEndIdx(endIdx);

        return ld.getIndices();
    }

    public ArrayList<Block> generateBlocks(int level) {
        System.out.println();
        initLevels();

        int[] activeBlocks = getBlockIdxs(level);
        x = new Integer[sizeX * sizeY];
        y = new Integer[sizeX * sizeY];

        setBitmapParams();

        int t = (int)AppConstants.getGridStep();
        int shiftX = 0, shiftY = 0;

        int numBlocksX = AppConstants.getBitmapBank().getNumBlocksX();
        int numBlocksY = AppConstants.getBitmapBank().getNumBlocksY();
        int numBlocks  = AppConstants.getBitmapBank().getNumBlocks();

        for (int i = 0; i < numBlocks; i++) {
            if (i % numBlocksX != 0)
                shiftX += t;
            else
                shiftX = t / 2;

            shiftY = (i / numBlocksX) * t + t;
            x[i] = shiftX;
            y[i] = shiftY;
        }

        ArrayList<Block> blocks = new ArrayList<>();


        Block block_start = new Block(BlockType.START, x[startIdx], y[startIdx]);
        block_start.setIdx(startIdx);
        blocks.add(0, block_start);

        int j = 1;
        for (int i = 0; i < numBlocks; i++) {
            if (i != startIdx && i != endIdx) {
                Block block;
                if (activeBlocks[i] == 1)
                    block = new Block(BlockType.DESTROYABLE_1, x[i], y[i]);
                else
                    block = new Block(BlockType.EMPTY, x[i], y[i]);
                blocks.add(j, block);
                block.setIdx(i);
                j++;
            }
        }


        Block block_end = new Block(BlockType.END, x[endIdx], y[endIdx]);
        blocks.add(numBlocks-1, block_end);
        block_end.setIdx(endIdx);


        return blocks;
    }

}
