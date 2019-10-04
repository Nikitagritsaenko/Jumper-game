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
    public static Integer[] x;
    public static Integer[] y;

    private void setBitmapParams() {
        AppConstants.getBitmapBank().setNumBlocksX(sizeX);
        AppConstants.getBitmapBank().setNumBlocksY(sizeY);
        AppConstants.getBitmapBank().setNumBlocks(sizeY * sizeX);

        double stepX = AppConstants.getScreenWidth() / (sizeX + 1.5);
        double stepY = AppConstants.getScreenHeight() / (sizeY + 1.5);
        int step = min((int)stepX, (int)stepY);

        AppConstants.getBitmapBank().setStep(step);
        AppConstants.setGridStep(step);

        AppConstants.getBitmapBank().scalePlayer(step / 2.0);
        AppConstants.getGameEngine().getPlayer().setFlipped(false);
        AppConstants.getBitmapBank().initBlocks();

        AppConstants.getBitmapBank().maximizeAllBlocks();

    }

    public void initLevels() {
        if (data.size() != 0)
            return;
        // ALL LEVELS DATA SETS HERE ------------------------------------------------------------------------------------------------

        data.add(new LevelData(1, 6, 8, 5, 2, new int[]
                        {0, 0, 0, 0, 0,
                        0, 1, 1, 1, 0,}));

        data.add(new LevelData(2, 6, 17, 5, 4, new int[]
                                {
                                0, 0, 0, 0, 0,
                                0, 1, 1, 0, 0,
                                0, 0, 1, 0, 0,
                                0, 0, 1, 0, 0,}));

        data.add(new LevelData(3, 7, 20, 7, 3, new int[]{
                0, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 1, 0, 1, 0,
                0, 1, 1, 0, 1, 0, 1}));

        data.add(new LevelData(4, 17, 31, 7, 5, new int[]
                        {0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0, 1, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 1}));


        data.add(new LevelData(5, 9, 44, 9, 5, new int[]{
                                                                                         0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                                                         1, 0, 0, 1, 0, 1, 0, 0, 0,
                                                                                         0, 0, 1, 0, 1, 0, 0, 0, 1,
                                                                                         0, 1, 0, 0, 0, 1, 0, 1, 0,
                                                                                         0, 0, 0, 0, 0, 1, 1, 0, 1}));

        data.add(new LevelData(6, 13, 28, 7, 5, new int[]
                        {
                                0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 1,
                                0, 0, 1, 0, 0, 1, 0,
                                1, 1, 0, 1, 0, 1, 0,
                                1, 0, 1, 0, 1, 0, 0}));

        data.add(new LevelData(7, 8, 31, 8, 4, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0, 0,
                        1, 1, 0, 0, 1, 0, 0, 1,
                        0, 0, 1, 1, 1, 0, 1, 1,
                        0, 1, 0, 0, 1, 1, 0, 1}));


        data.add(new LevelData(8, 7, 28, 7, 5, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0,
                        1, 0, 0, 0, 0, 1, 0,
                        0, 1, 0, 1, 1, 0, 1,
                        0, 1, 1, 1, 1, 1, 0,
                        1, 1, 1, 1, 0, 0, 0}));

        data.add(new LevelData(9, 6, 9, 5, 2, new int[]
                        {0, 0, 0, 0, 0,
                        0, 1, 2, 2, 1,}));

        data.add(new LevelData(10, 6, 16, 5, 4, new int[]
                        {
                                0, 0, 0, 0, 0,
                                0, 1, 2, 2, 1,
                                0, 0, 0, 0, 0,
                                0, 1, 2, 2, 1,}));

        data.add(new LevelData(11, 13, 35, 9, 6, new int[]
                        {       0, 0, 0, 0, 0, 0, 0, 0, 0,
                                0, 1, 1, 1, 1, 0, 0, 0, 0,
                                0, 1, 0, 0, 0, 0, 0, 0, 0,
                                0, 1, 0, 0, 0, 0, 0, 0, 1,
                                0, 1, 0, 2, 2, 0, 2, 2, 0,
                                0, 1, 1, 0, 0, 1, 0, 0, 0}));

        data.add(new LevelData(12, 14, 28, 7, 5, new int[]
                         {0, 0, 0, 0, 0, 0, 0,
                         0, 0, 0, 0, 0, 1, 0,
                         1, 1, 1, 1, 1, 0, 1,
                         0, 1, 0, 0, 0, 1, 0,
                         1, 1, 2, 1, 1, 1, 0}));

        data.add(new LevelData(13, 21, 34, 7, 5, new int[]
                       {
                                 0, 0, 0, 0, 0, 0, 0,
                                 0, 0, 1, 1, 0, 0, 0,
                                 0, 1, 0, 0, 0, 0, 1,
                                 0, 0, 0, 0, 2, 2, 1,
                                 0, 0, 0, 2, 2, 1, 0}));


        data.add(new LevelData(14, 50, 51, 9, 6, new int[]
                                {
                                        //0, 0, 0, 0, 0, 0, 0, 0, 0,
                                        0, 0, 1, 0, 0, 0, 0, 0, 0,
                                        0, 1, 0, 1, 1, 1, 0, 0, 0,
                                        1, 0, 0, 0, 0, 0, 1, 1, 0,
                                        0, 1, 0, 0, 0, 0, 0, 1, 0,
                                        1, 0, 0, 2, 0, 0, 1, 1, 0,
                                        0, 1, 1, 0, 2, 1, 1, 2, 2,}));


        data.add(new LevelData(15, 34, 54, 10, 6, new int[]
                        {
                                //0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 1, 0, 1, 0, 0, 0,
                                0, 1, 0, 1, 0, 1, 2, 2, 0, 1,
                                1, 0, 1, 0, 0, 0, 0, 0, 1, 1,
                                0, 1, 0, 0, 1, 0, 0, 0, 0, 0,
                                1, 0, 0, 1, 0, 0, 0, 0, 2, 2,
                                0, 1, 1, 0, 1, 1, 1, 1, 0, 0,}));

        data.add(new LevelData(16, 40, 43, 10, 5, new int[]
                {       //0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 1, 1, 2, 2, 1,
                        0, 2, 0, 2, 2, 0, 0, 0, 0, 0,
                        2, 0, 2, 0, 0, 1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0, 0, 2, 2, 1, 1,
                        1, 0, 0, 1, 0, 0, 0, 0, 0, 0,}));

        data.add(new LevelData(17, 0, 59, 10, 6, new int[]
                {
                        //0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        1, 0, 2, 0, 0, 0, 0, 0, 1, 0,
                        0, 2, 0, 0, 0, 0, 0, 1, 0, 2,
                        1, 0, 1, 0, 0, 0, 0, 0, 2, 0,
                        0, 2, 0, 1, 0, 0, 0, 1, 0, 1,
                        1, 0, 2, 0, 1, 0, 1, 0, 2, 0,
                        0, 1, 0, 1, 0, 1, 0, 1, 0, 1,}));

        data.add(new LevelData(18, 50, 59, 10, 6, new int[]
                {
                        //0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 1, 0, 0, 1, 1, 0, 0, 0,
                        1, 0, 0, 2, 2, 0, 0, 1, 0, 0,
                        0, 1, 1, 0, 0, 2, 1, 0, 0, 0,
                        1, 0, 0, 1, 2, 0, 0, 1, 0, 0,
                        0, 1, 1, 0, 0, 0, 2, 0, 0, 1,
                        1, 0, 0, 2, 2, 2, 0, 2, 2, 0,}));

        data.add(new LevelData(19, 31, 50, 10, 6, new int[]
                {
                        //0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 1, 1, 0, 0, 0, 1, 0, 0,
                        0, 2, 0, 0, 1, 0, 1, 0, 1, 1,
                        0, 0, 2, 0, 0, 0, 0, 1, 0, 1,
                        0, 0, 0, 1, 2, 0, 1, 0, 0, 0,
                        0, 0, 0, 2, 0, 2, 0, 0, 1, 0,
                        1, 1, 2, 0, 0, 0, 1, 2, 2, 0,}));

        data.add(new LevelData(20, 36, 8, 9, 5, new int[]
                        {
                                //0, 0, 0, 0, 0, 0, 0, 0, 0,
                                0, 1, 1, 2, 2, 2, 2, 0, 1,
                                1, 0, 0, 0, 0, 0, 0, 2, 0,
                                0, 2, 2, 1, 1, 1, 1, 0, 1,
                                0, 0, 0, 0, 0, 0, 0, 1, 0,
                                1, 1, 2, 2, 2, 2, 1, 0, 0, }));
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

            shiftY = t / 2 + (i / numBlocksX) * t + t;
            x[i] = shiftX;
            y[i] = shiftY;
        }

        ArrayList<Block> blocks = new ArrayList<>();


        Block block_start = new Block(BlockType.START, x[startIdx], y[startIdx]);
        block_start.setIdx(startIdx);
        block_start.setPos(0);
        blocks.add(0, block_start);

        for (int i = 0; i < numBlocks; i++) {
            if (i != startIdx && i != endIdx) {
                Block block;
                if (activeBlocks[i] == 1)
                    block = new Block(BlockType.DESTROYABLE_1, x[i], y[i]);
                else if (activeBlocks[i] == 2)
                    block = new Block(BlockType.DESTROYABLE_2, x[i], y[i]);
                else
                    block = new Block(BlockType.EMPTY, x[i], y[i]);
                blocks.add(block);
                block.setIdx(i);
                block.setPos(blocks.size()-1);
            }
        }


        Block block_end = new Block(BlockType.END, x[endIdx], y[endIdx]);
        blocks.add( block_end);
        block_end.setIdx(endIdx);
        block_end.setPos(blocks.size()-1);

        return blocks;
    }

}
