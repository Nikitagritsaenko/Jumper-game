package edu.amd.spbstu.jumper;

import java.util.ArrayList;

public class LevelGenerator {
    public static ArrayList<LevelData> data = new ArrayList<>();
    private int sizeX, sizeY;
    private int startIdx, endIdx;
    public static Integer[] x;
    public static Integer[] y;

    private void setBitmapParams() {
        AppConstants.getBitmapBank().setNumBlocksX(sizeX);
        AppConstants.getBitmapBank().setNumBlocksY(sizeY);
        AppConstants.getBitmapBank().setNumBlocks(sizeY * sizeX);

        int stepX = AppConstants.getScreenWidth() / (sizeX + 1);
        int stepY = (int)((double) AppConstants.getScreenHeight() / (sizeY + 1.5));

        AppConstants.getBitmapBank().setStepX(stepX);
        AppConstants.getBitmapBank().setStepY(stepY);

        AppConstants.setGridStepX(stepX);
        AppConstants.setGridStepY(stepY);

        AppConstants.getBitmapBank().scalePlayer(Math.min(stepX / 2.0, stepY / 2.0));
        AppConstants.getGameEngine().getPlayer().setFlipped(false);

    }

    public void initLevels() {
        if (data.size() != 0)
            return;
        // ALL LEVELS DATA SETS HERE ------------------------------------------------------------------------------------------------

        data.add(new LevelData(1, 6, 8, 5, 2, new int[]
                        {0, 0, 0, 0, 0,
                        0, 1, 1, 1, 0,}));

        data.add(new LevelData(2, 0, 8, 5, 2, new int[]
                        {1, 0, 0, 0, 0,
                        0, 1, 1, 1, 1,
                }));

        data.add(new LevelData(3, 1, 16, 5, 4, new int[]
                        {0, 1, 1, 0, 0,
                         0, 0, 0, 1, 0,
                         0, 0, 0, 0, 0,
                         0, 1, 1, 1, 1,}));


        data.add(new LevelData(4, 0, 35, 9, 4, new int[]{
                                                                                         1, 0, 0, 1, 0, 1, 0, 0, 0,
                                                                                         0, 0, 1, 0, 1, 0, 0, 0, 1,
                                                                                         0, 1, 0, 0, 0, 1, 0, 1, 0,
                                                                                         0, 0, 0, 0, 0, 1, 1, 0, 1}));

        data.add(new LevelData(5, 6, 21, 7, 4, new int[]
                        {
                                0, 0, 0, 0, 0, 0, 1,
                                0, 0, 1, 0, 0, 1, 0,
                                1, 1, 0, 1, 0, 1, 0,
                                1, 0, 1, 0, 1, 0, 0}));

        data.add(new LevelData(6, 0, 23, 8, 3, new int[]
                {
                        1, 1, 0, 0, 1, 0, 0, 1,
                        0, 0, 1, 1, 1, 0, 1, 1,
                        0, 1, 0, 0, 1, 1, 0, 1}));


        data.add(new LevelData(7, 1, 25, 8, 4, new int[]
                {
                        0, 1, 0, 0, 0, 0, 1, 0,
                        0, 0, 1, 0, 1, 1, 0, 1,
                        0, 0, 1, 1, 1, 1, 1, 0,
                        0, 1, 1, 1, 1, 0, 0, 0}));

        data.add(new LevelData(8, 6, 9, 5, 2, new int[]
                        {0, 0, 0, 0, 0,
                        0, 1, 2, 2, 1,}));

        data.add(new LevelData(9, 1, 13, 6, 3, new int[]
                        {
                                0, 1, 2, 2, 1, 0,
                                0, 0, 0, 0, 0, 0,
                                0, 1, 2, 2, 1, 0}));

        data.add(new LevelData(10, 5, 29, 10, 5, new int[]
                        {
                                0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
                                0, 0, 1, 0, 1, 0, 0, 0, 0, 0,
                                0, 0, 1, 1, 0, 0, 1, 0, 0, 1,
                                0, 0, 1, 0, 2, 2, 0, 2, 2, 0,
                                0, 0, 1, 1, 0, 0, 1, 0, 0, 0}));


        data.add(new LevelData(11, 11, 29, 9, 4, new int[]
                         {
                                 0, 0, 0, 0, 0, 0, 0, 1, 0,
                                 0, 0, 1, 1, 1, 1, 1, 0, 1,
                                 0, 0, 0, 1, 0, 0, 0, 1, 0,
                                 0, 0, 1, 1, 2, 1, 1, 1, 0}));

        data.add(new LevelData(12, 10, 31, 8, 4, new int[]
                       {
                                 0, 0, 0, 1, 1, 0, 0, 0,
                                 0, 0, 1, 0, 0, 0, 0, 1,
                                 0, 0, 0, 0, 0, 2, 2, 1,
                                 0, 0, 0, 0, 2, 2, 1, 0}));

        data.add(new LevelData(13, 45, 48, 11, 5, new int[]
                {
                        0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 1,
                        0, 0, 2, 0, 2, 2, 0, 0, 0, 0, 0,
                        0, 2, 0, 2, 0, 0, 1, 0, 0, 0, 0,
                        0, 0, 1, 0, 0, 0, 0, 2, 2, 1, 1,
                        0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,}));

        data.add(new LevelData(14, 1, 65, 11, 6, new int[]
                {
                        0, 1, 0, 2, 0, 0, 0, 0, 0, 1, 0,
                        0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 2,
                        0, 1, 0, 1, 0, 0, 0, 0, 0, 2, 0,
                        0, 0, 2, 0, 1, 0, 0, 0, 1, 0, 1,
                        0, 1, 0, 2, 0, 1, 0, 1, 0, 2, 0,
                        0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,}));

        data.add(new LevelData(15, 56, 65, 11, 6, new int[]
                {
                        0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0,
                        0, 1, 0, 0, 2, 2, 0, 0, 1, 0, 0,
                        0, 0, 1, 1, 0, 0, 2, 1, 0, 0, 0,
                        0, 1, 0, 0, 1, 2, 0, 0, 1, 0, 0,
                        0, 0, 1, 1, 0, 0, 0, 2, 0, 0, 1,
                        0, 1, 0, 0, 2, 2, 2, 0, 2, 2, 0,}));

        data.add(new LevelData(16, 35, 56, 11, 6, new int[]
                {
                        0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0,
                        0, 0, 2, 0, 0, 1, 0, 1, 0, 1, 1,
                        0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 1,
                        0, 0, 0, 0, 1, 2, 0, 1, 0, 0, 0,
                        0, 0, 0, 0, 2, 0, 2, 0, 0, 1, 0,
                        0, 1, 1, 2, 0, 0, 0, 1, 2, 2, 0,}));

        data.add(new LevelData(17, 40, 9, 10, 5, new int[]
                {
                        0, 0, 1, 1, 2, 2, 2, 2, 0, 1,
                        0, 1, 0, 0, 0, 0, 0, 0, 2, 0,
                        0, 0, 2, 2, 1, 1, 1, 1, 0, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                        0, 1, 1, 2, 2, 2, 2, 1, 0, 0, }));


        data.add(new LevelData(18, 21, 13, 7, 4, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 1, 0, 1, 0, 1,
                        0, 0, 0, 0, 0, 0, 0,
                        1, 3, 0, 3, 0, 3, 0}));

        data.add(new LevelData(19, 17, 21, 8, 3, new int[]
                {
                        0, 0, 0, 0, 2, 1, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 1, 3, 0, 1, 0, 0}));

        data.add(new LevelData(20, 17, 20, 8, 3, new int[]
                {
                        0, 0, 0, 1, 1, 1, 2, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 3, 0, 1, 2, 2, 3}));


        data.add(new LevelData(21, 18, 22, 8, 5, new int[]
                {
                        0, 0, 1, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 3, 1, 0, 0, 2, 1, 0,
                        0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 3, 1, 1, 3}));

        data.add(new LevelData(22, 1, 49, 10, 5, new int[]
                {
                        0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 1, 1, 1, 0, 1, 1, 1, 0,
                        0, 0, 1, 2, 1, 0, 1, 2, 1, 0,
                        0, 0, 1, 1, 1, 3, 1, 1, 1, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        }));

        data.add(new LevelData(23, 10, 54, 11, 6, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0,
                        0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0,
                        3, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0,
                        0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,}));

        data.add(new LevelData(24, 0, 25, 10, 5, new int[]
                {
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
                        0, 1, 0, 0, 3, 1, 0, 0, 0, 1,
                        0, 1, 3, 0, 0, 0, 3, 0, 3, 1,
                        0, 1, 1, 3, 3, 1, 1, 3, 1, 1,
                       }));

        data.add(new LevelData(25, 62, 63, 11, 6, new int[]
                                {
                                        0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
                                        0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0,
                                        0, 0, 2, 0, 3, 0, 0, 0, 1, 2, 0,
                                        0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0,
                                        0, 3, 0, 0, 0, 2, 0, 0, 1, 1, 3,
                                        0, 0, 0, 0, 3, 0, 2, 1, 1, 2, 2,}));


        data.add(new LevelData(26, 38, 60, 11, 6, new int[]
                        {
                                0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1,
                                0, 0, 1, 0, 1, 0, 0, 2, 2, 0, 0,
                                0, 1, 0, 2, 0, 0, 3, 0, 0, 3, 1,
                                0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 1, 0, 0, 0, 0, 2, 2,
                                0, 3, 0, 3, 0, 1, 1, 1, 1, 0, 0,}));



        data.add(new LevelData(27, 38, 39, 11, 6, new int[]
                {
                        0, 0, 0, 1, 1, 2, 2, 1, 1, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 3, 0, 0, 0, 0, 0, 0, 2, 0,
                        0, 0, 0, 3, 0, 1, 1, 1, 0, 0, 0,
                        3, 1, 0, 1, 3, 0, 0, 0, 3, 0, 3,
                        0, 1, 1, 0, 0, 0, 0, 0, 3, 1, 0,}));



        data.add(new LevelData(28, 0, 13, 10, 6, new int[]
                {
                        1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
                        0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 3, 0, 3, 0, 3, 0, 3,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 2, 1, 0, 3, 0,
                        0, 1, 2, 2, 0, 1, 2, 1, 0, 0,}));


        data.add(new LevelData(29, 0, 8, 11, 6, new int[]
                {
                        1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1,
                        0, 0, 0, 3, 0, 0, 1, 0, 0, 2, 0,
                        0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0,
                        0, 2, 0, 0, 3, 0, 1, 0, 0, 1, 0,
                        3, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0,
                        0, 2, 2, 3, 0, 1, 1, 0, 0, 1, 1,}));

        data.add(new LevelData(30, 0, 27, 11, 6, new int[]
                {
                        1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0,
                        0, 0, 0, 1, 1, 1, 2, 2, 0, 0, 1,
                        0, 0, 2, 0, 0, 1, 0, 0, 2, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 2, 0, 3, 0,
                        0, 3, 1, 3, 0, 0, 2, 0, 0, 0, 0,
                        0, 3, 0, 0, 0, 1, 0, 0, 3, 0, 0,}));

        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                        {1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));

        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 0, 4, 0,
                        0, 4, 0, 1, 0,
                }));
        data.add(new LevelData(31, 0, 8, 5, 2, new int[]
                {       1, 0, 4, 0, 4,
                        0, 1, 0, 1, 1,
                }));
        data.add(new LevelData(23, 10, 54, 11, 6, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 4,
                        0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0,
                        4, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0,
                        0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1,
                        0, 3, 0, 0, 0, 1, 0, 0, 0, 1, 0,}));
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
        initLevels();

        int[] activeBlocks = getBlockIdxs(level);
        x = new Integer[sizeX * sizeY];
        y = new Integer[sizeX * sizeY];

        setBitmapParams();

        int tx = (int)AppConstants.getGridStepX();
        int ty = (int)AppConstants.getGridStepY();

        int shiftX = 0, shiftY = 0;

        int numBlocksX = AppConstants.getBitmapBank().getNumBlocksX();
        int numBlocksY = AppConstants.getBitmapBank().getNumBlocksY();
        int numBlocks  = AppConstants.getBitmapBank().getNumBlocks();

        for (int i = 0; i < numBlocks; i++) {
            if (i % numBlocksX != 0)
                shiftX += tx;
            else
                shiftX = tx / 2;

            shiftY = ty / 2 + (i / numBlocksX) * ty + ty;
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
                else if (activeBlocks[i] == 3)
                    block = new Block(BlockType.SPRING, x[i], y[i]);
                else if (activeBlocks[i] == 4) {
                    block = new Block(BlockType.PORTAL, x[i], y[i]);
                }
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
