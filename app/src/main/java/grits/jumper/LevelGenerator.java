package grits.jumper;

import java.util.ArrayList;

class LevelGenerator {

    private static ArrayList<LevelData> data = new ArrayList<>();

    private int sizeX, sizeY;
    private int startIdx, endIdx;

    private void setBitmapParams() {
        AppConstants.getBitmapBank().setNumBlocksX(sizeX);
        AppConstants.getBitmapBank().setNumBlocksY(sizeY);
        AppConstants.getBitmapBank().setNumBlocks(sizeY * sizeX);

        int stepX = AppConstants.getScreenWidth() / (sizeX + 1);
        int stepY = (int)((double) AppConstants.getScreenHeight() / (sizeY + 1.5));

        AppConstants.setGridStepX(stepX);
        AppConstants.setGridStepY(stepY);
        AppConstants.setGravity(stepY / AppConstants.GRAVITY_SCALE_COEFFICIENT);

        AppConstants.getBitmapBank().scalePlayer(Math.min(stepX / 2.0, stepY / 2.0));
        AppConstants.getGameEngine().getPlayer().setFlipped(false);

    }

    private void initLevels() {

        if (data.size() != 0) {
            return;
        }

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

        data.add(new LevelData(10, 3, 23, 8, 5, new int[]
                        {
                                1, 1, 1, 1, 0, 0, 0, 0,
                                1, 0, 1, 0, 0, 0, 0, 0,
                                1, 1, 0, 0, 1, 0, 0, 1,
                                1, 0, 2, 2, 0, 2, 2, 0,
                                1, 1, 0, 0, 1, 0, 0, 0}));


        data.add(new LevelData(11, 7, 21, 7, 4, new int[]
                         {
                                 0, 0, 0, 0, 0, 1, 0,
                                 1, 1, 1, 1, 1, 0, 1,
                                 0, 1, 0, 0, 0, 1, 0,
                                 1, 1, 2, 1, 1, 1, 0}));

        data.add(new LevelData(12, 6, 23, 6, 4, new int[]
                       {
                                 0, 1, 1, 0, 0, 0,
                                 1, 0, 0, 0, 0, 1,
                                 0, 0, 0, 2, 2, 1,
                                 0, 0, 2, 2, 1, 1}));

        data.add(new LevelData(13, 40, 43, 10, 5, new int[]
                {
                        0, 0, 0, 0, 0, 1, 1, 2, 2, 1,
                        0, 2, 0, 2, 2, 0, 0, 0, 0, 0,
                        2, 0, 2, 0, 0, 1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0, 0, 2, 2, 1, 1,
                        1, 0, 0, 1, 0, 0, 0, 0, 0, 0,}));

        data.add(new LevelData(14, 0, 59, 10, 6, new int[]
                {
                        1, 0, 2, 0, 0, 0, 0, 0, 1, 0,
                        0, 2, 0, 0, 0, 0, 0, 1, 0, 2,
                        1, 0, 1, 0, 0, 0, 0, 0, 2, 0,
                        0, 2, 0, 1, 0, 0, 0, 1, 0, 1,
                        1, 0, 2, 0, 1, 0, 1, 0, 2, 0,
                        0, 1, 0, 1, 0, 1, 0, 1, 0, 1,}));

        data.add(new LevelData(15, 50, 59, 10, 6, new int[]
                {
                        0, 1, 1, 0, 0, 1, 1, 0, 0, 0,
                        1, 0, 0, 2, 2, 0, 0, 1, 0, 0,
                        0, 1, 1, 0, 0, 2, 1, 0, 0, 0,
                        1, 0, 0, 1, 2, 0, 0, 1, 0, 0,
                        0, 1, 1, 0, 0, 0, 2, 0, 0, 1,
                        1, 0, 0, 2, 2, 2, 0, 2, 2, 1,}));

        data.add(new LevelData(16, 31, 50, 10, 6, new int[]
                {
                        0, 0, 1, 1, 0, 0, 0, 1, 0, 0,
                        0, 2, 0, 0, 1, 0, 1, 0, 1, 1,
                        0, 0, 2, 0, 0, 0, 0, 1, 0, 1,
                        0, 1, 0, 1, 2, 0, 1, 0, 0, 0,
                        0, 0, 0, 2, 0, 2, 0, 0, 1, 0,
                        1, 1, 2, 0, 0, 0, 1, 2, 2, 0,}));

        data.add(new LevelData(17, 40, 9, 10, 5, new int[]
                {
                        0, 0, 1, 1, 2, 2, 2, 2, 0, 1,
                        0, 1, 0, 0, 0, 0, 0, 0, 2, 0,
                        0, 0, 2, 2, 1, 1, 1, 1, 0, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                        1, 1, 1, 2, 2, 2, 2, 1, 0, 0, }));


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

        data.add(new LevelData(25, 56, 57, 10, 6, new int[]
                                {
                                        0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
                                        0, 0, 1, 0, 1, 1, 1, 0, 0, 0,
                                        0, 2, 0, 3, 0, 0, 0, 1, 2, 0,
                                        0, 0, 1, 0, 0, 0, 0, 0, 1, 0,
                                        3, 0, 0, 0, 2, 0, 0, 1, 1, 3,
                                        0, 0, 0, 3, 0, 2, 1, 1, 2, 2,}));


        data.add(new LevelData(26, 34, 54, 10, 6, new int[]
                        {
                                0, 0, 0, 0, 1, 1, 1, 0, 0, 1,
                                0, 1, 0, 1, 0, 0, 2, 2, 0, 0,
                                1, 0, 2, 0, 0, 3, 0, 0, 3, 1,
                                0, 2, 0, 0, 1, 0, 0, 0, 0, 0,
                                0, 0, 0, 1, 0, 0, 0, 0, 2, 2,
                                3, 0, 3, 0, 1, 1, 1, 1, 0, 0,}));



        data.add(new LevelData(27, 38, 39, 11, 6, new int[]
                {
                        0, 0, 0, 1, 1, 2, 2, 1, 1, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 3, 0, 0, 0, 0, 0, 0, 2, 0,
                        0, 0, 0, 3, 0, 1, 1, 1, 0, 0, 0,
                        3, 1, 0, 1, 3, 0, 0, 0, 3, 0, 3,
                        0, 1, 1, 0, 0, 0, 0, 0, 3, 1, 0,}));

        data.add(new LevelData(28, 0, 12, 10, 6, new int[]
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

        data.add(new LevelData(30, 0, 27, 7, 4, new int[]
                {
                        1, 0, 0, 0, 0, 0, 4,
                        0, 0, 4, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        0, 3, 0, 0, 0, 0, 1}));

        data.add(new LevelData(31, 0, 27, 7, 4, new int[]
                {
                        1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 4,
                        0, 0, 0, 0, 3, 0, 0,
                        4, 2, 1, 0, 1, 2, 1}));

        data.add(new LevelData(32, 23, 21, 7, 4, new int[]
                {
                        0, 4, 0, 0, 0, 4, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        1, 3, 1, 1, 2, 2, 3}));

        data.add(new LevelData(33, 19, 50, 10, 6, new int[]
                {
                        0, 0, 0, 0, 0, 0, 4, 0, 0, 0,
                        4, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 3, 0, 3, 0, 0,
                        0, 0, 0, 0, 2, 0, 0, 0, 0, 0,
                        0, 1, 0, 2, 0, 0, 3, 0, 3, 0,
                        1, 0, 1, 0, 0, 3, 0, 0, 0, 0,}));

        data.add(new LevelData(34, 0, 59, 10, 6, new int[]
                {
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 4,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 1, 2, 0, 1, 2, 0, 2, 1,
                        0, 0, 0, 0, 0, 0, 0, 2, 0, 0,
                        0, 3, 2, 2, 3, 0, 0, 0, 0, 0,
                        0, 0, 0, 4, 0, 0, 0, 0, 0, 1,}));

        data.add(new LevelData(35, 50, 51, 10, 6, new int[]
                {
                        4, 0, 0, 0, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                        0, 1, 1, 0, 3, 0, 0, 1, 0, 1,
                        2, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                        0, 1, 0, 1, 0, 3, 0, 0, 0, 1,
                        1, 1, 0, 0, 1, 0, 0, 0, 4, 0,}));

        data.add(new LevelData(36, 24, 25, 10, 6, new int[]
                {
                        0, 4, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 1, 0, 0, 0, 0,
                        0, 2, 3, 0, 0, 0, 2, 0, 1, 0,
                        0, 0, 0, 3, 0, 0, 0, 0, 0, 0,
                        3, 0, 1, 0, 0, 0, 0, 3, 0, 4,}));

        data.add(new LevelData(37, 0, 53, 10, 6, new int[]
                {
                        1, 0, 1, 0, 0, 4, 0, 0, 0, 0,
                        0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 0, 2, 2, 0, 1,
                        0, 0, 0, 0, 0, 1, 0, 0, 2, 0,
                        0, 0, 3, 0, 0, 3, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 0, 0, 0, 4, 3,}));

        data.add(new LevelData(38, 0, 59, 10, 6, new int[]
                {
                        1, 0, 0, 1, 1, 0, 0, 0, 0, 4,
                        0, 0, 1, 0, 0, 1, 1, 0, 1, 0,
                        0, 0, 0, 0, 2, 0, 0, 0, 0, 1,
                        1, 1, 0, 3, 0, 2, 0, 3, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        1, 1, 3, 0, 4, 0, 1, 2, 2, 1,}));

        data.add(new LevelData(39, 0, 27, 11, 6, new int[]
                {
                        1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0,
                        0, 0, 0, 1, 1, 1, 2, 2, 4, 0, 1,
                        0, 0, 2, 0, 0, 1, 0, 0, 1, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 1, 0, 3, 0,
                        0, 3, 1, 3, 3, 0, 1, 0, 0, 0, 0,
                        0, 3, 0, 0, 0, 1, 0, 0, 4, 0, 0,}));


        data.add(new LevelData(40, 72, 11, 12, 7, new int[]
                {
                        0, 0, 0, 0, 0, 0, 4, 0, 1, 0, 0, 1,
                        0, 0, 1, 2, 2, 2, 2, 1, 0, 0, 1, 0,
                        0, 2, 0, 0, 0, 0, 3, 0, 0, 1, 3, 1,
                        3, 0, 1, 0, 0, 3, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 3, 0, 0, 1, 0, 1, 1, 0, 4,
                        1, 2, 2, 0, 0, 1, 0, 1, 2, 2, 1, 0,
                        }));

        data.add(new LevelData(41, 7, 10, 6, 3, new int[]
                {
                        0, 0, 0, 0, 0, 0,
                        0, 1, 0, 0, 1, 0,
                        0, 0, 1, 5, 0, 0,
                }));

        data.add(new LevelData(42, 7, 26, 7, 4, new int[]
                {
                        0, 0, 5, 0, 5, 0, 0,
                        1, 0, 0, 0, 0, 0, 0,
                        0, 3, 0, 3, 0, 0, 0,
                        0, 0, 0, 0, 0, 1, 0}));

        data.add(new LevelData(43, 7, 21, 7, 4, new int[]
                {
                        0, 4, 0, 0, 0, 0, 4,
                        1, 0, 0, 0, 0, 0, 0,
                        0, 5, 1, 5, 5, 1, 5,
                        1, 0, 0, 0, 0, 0, 0}));

        data.add(new LevelData(44, 19, 13, 10, 6, new int[]
                {
                        0, 5, 5, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 0, 0, 0, 0, 1,
                        3, 0, 0, 0, 0, 5, 5, 5, 5, 0,
                        0, 1, 0, 0, 0, 5, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 5, 0, 0, 0, 0,
                        0, 0, 3, 1, 1, 1, 0, 0, 0, 0}));

        data.add(new LevelData(45, 7, 21, 7, 4, new int[]
                {
                        0, 0, 6, 0, 0, 0, 6,
                        1, 2, 0, 0, 0, 3, 1,
                        0, 0, 0, 0, 1, 0, 1,
                        0, 0, 3, 0, 0, 1, 1}));

        data.add(new LevelData(46, 0, 30, 10, 6, new int[]
                {
                        1, 0, 1, 5, 0, 0, 0, 0, 1, 0,
                        0, 0, 0, 0, 2, 2, 1, 0, 0, 0,
                        0, 3, 0, 0, 0, 0, 0, 0, 0, 3,
                        1, 1, 0, 0, 0, 0, 0, 0, 1, 0,
                        0, 0, 2, 2, 0, 0, 6, 0, 0, 6,
                        0, 0, 0, 0, 5, 1, 1, 0, 0, 3}));

        data.add(new LevelData(47, 28, 34, 7, 5, new int[]
                {
                        0, 0, 7, 0, 7, 0, 7,
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        1, 7, 0, 7, 0, 7, 1}));

        data.add(new LevelData(48, 41, 75, 12, 7, new int[]
                {
                        0, 7, 0, 0, 1, 2, 2, 1, 5, 1, 0, 0,
                        0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 6, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        6, 0, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 5, 1, 1, 5, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0,
                        1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(49, 0, 59, 12, 7, new int[]
                {
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6,
                        0, 5, 7, 7, 0, 7, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 3, 2, 0, 0, 0,
                        0, 6, 0, 0, 0, 0, 0, 0, 0, 2, 5, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1,
                        0, 0, 7, 0, 7, 0, 7, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(50, 72, 81, 12, 7, new int[]
                {
                        0, 0, 0, 4, 0, 0, 0, 0, 0, 7, 0, 0,
                        4, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 5, 5, 0, 0, 0, 0, 5, 5, 0,
                        3, 1, 0, 0, 1, 0, 0, 0, 0, 0, 2, 2,
                        0, 0, 0, 0, 1, 0, 0, 0, 7, 0, 0, 1,
                        0, 0, 7, 0, 1, 2, 2, 3, 0, 0, 0, 1,
                        1, 3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 5,
                }));

        data.add(new LevelData(51, 40, 46, 12, 7, new int[]
                {
                        7, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0,
                        0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 6, 0,
                        0, 0, 0, 6, 1, 0, 0, 0, 0, 0, 0, 0,
                        7, 0, 0, 1, 0, 0, 0, 0, 5, 3, 0, 0,
                        0, 2, 2, 1, 0, 5, 7, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(52, 47, 14, 14, 10, new int[]
                {
                        6, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0,
                        1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 5, 0, 1, 1,
                        0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1,
                        1, 0, 1, 0, 0, 1, 2, 2, 0, 0, 0, 0, 0, 1,
                        0, 1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 2, 1,
                        1, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 5, 0, 0,
                        5, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                        0, 0, 0, 6, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 3, 1, 0, 7, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(53, 13, 87, 14, 10, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        0, 4, 0, 0, 1, 1, 2, 2, 1, 0, 0, 0, 1, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0,
                        1, 5, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1,
                        0, 0, 0, 1, 4, 0, 0, 0, 0, 5, 3, 0, 1, 0,
                        2, 2, 0, 0, 0, 0, 0, 0, 5, 0, 0, 2, 0, 0,
                        0, 0, 3, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0,
                        0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 3,
                        0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(54, 126, 22, 14, 10, new int[]
                {
                        0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6,
                        5, 0, 0, 5, 0, 0, 0, 0, 1, 5, 1, 1, 1, 0,
                        0, 5, 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 5,
                        5, 0, 0, 5, 0, 0, 0, 1, 0, 0, 0, 0, 5, 3,
                        0, 5, 5, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0,
                        1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                        0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0,
                        0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                        1, 0, 0, 1, 1, 7, 0, 7, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(55, 29, 127, 14, 10, new int[]
                {
                        0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 1, 0,
                        0, 1, 0, 0, 0, 1, 0, 0, 0, 2, 1, 1, 0, 1,
                        0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        6, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0,
                        0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0,
                        7, 2, 0, 1, 5, 0, 1, 0, 5, 0, 0, 0, 3, 0,
                        3, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                }));

        data.add(new LevelData(56, 0, 168, 16, 11, new int[]
                {
                        1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1,
                        0, 0, 2, 0, 0, 3, 0, 0, 1, 0, 1, 0, 1, 0, 2, 0,
                        0, 1, 0, 1, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        5, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1,
                        0, 5, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0,
                        5, 0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0,
                        0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 5, 0,
                        0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 5, 0, 0,
                        0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                        0, 0, 1, 1, 0, 0, 7, 0, 1, 0, 0, 1, 1, 1, 1, 0,
                }));

        data.add(new LevelData(57, 1, 2, 4, 2, new int[]
                {
                        8, 1, 1, 8,
                        8, 8, 8, 8,
                }));

        // --------------------------------------------------------------------------------------------------------------------------
    }

    private int[] getBlockIndices(int level) {

        LevelData ld = data.get(level - 1);
        sizeX = ld.getNumX();
        sizeY = ld.getNumY();
        startIdx = ld.getStartIdx();
        endIdx = ld.getEndIdx();

        AppConstants.getGameEngine().setStartIdx(startIdx);
        AppConstants.getGameEngine().setEndIdx(endIdx);

        return ld.getIndices();
    }

    ArrayList<Block> generateBlocks(int level) {
        
        initLevels();

        int[] activeBlocks = getBlockIndices(level);
        Integer[] x = new Integer[sizeX * sizeY];
        Integer[] y = new Integer[sizeX * sizeY];

        setBitmapParams();

        int tx = AppConstants.getGridStepX();
        int ty = AppConstants.getGridStepY();

        int shiftX = 0, shiftY;

        int numBlocksX = AppConstants.getBitmapBank().getNumBlocksX();
        int numBlocks  = AppConstants.getBitmapBank().getNumBlocks();

        for (int i = 0; i < numBlocks; i++) {
            if (i % numBlocksX != 0) {
                shiftX += tx;
            }
            else {
                shiftX = tx / 2;
            }

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
                if (activeBlocks[i] == 1) {
                    block = new Block(BlockType.DESTROYABLE_1, x[i], y[i]);
                }
                else if (activeBlocks[i] == 2) {
                    block = new Block(BlockType.DESTROYABLE_2, x[i], y[i]);
                }
                else if (activeBlocks[i] == 3) {
                    block = new Block(BlockType.SPRING, x[i], y[i]);
                }
                else if (activeBlocks[i] == 4) {
                    block = new Block(BlockType.PORTAL, x[i], y[i]);
                }
                else if (activeBlocks[i] == 5) {
                    block = new Block(BlockType.LR_REVERSER, x[i], y[i]);
                }
                else if (activeBlocks[i] == 6) {
                    block = new Block(BlockType.PORTAL_REUSABLE, x[i], y[i]);
                }
                else if (activeBlocks[i] == 7) {
                    block = new Block(BlockType.GRAVITY_REVERSER, x[i], y[i]);
                }
                else if (activeBlocks[i] == 8) {
                    block = new Block(BlockType.ENDGAME_BONUS, x[i], y[i]);
                }
                else {
                    block = new Block(BlockType.EMPTY, x[i], y[i]);
                }
                
                blocks.add(block);
                block.setIdx(i);
                block.setPos(blocks.size() - 1);
            }
        }
        
        Block blockEnd = new Block(BlockType.END, x[endIdx], y[endIdx]);
        blocks.add(blockEnd);
        blockEnd.setIdx(endIdx);
        blockEnd.setPos(blocks.size() - 1);

        return blocks;
    }
}
