package edu.amd.spbstu.jumper;

import java.util.ArrayList;


public class LevelGenerator {
    private static Integer[] x = new Integer[6];
    private static Integer[] y = new Integer[6];

    public static ArrayList<Block> generateBlocks(int level) {

        x[0] = 100; x[1] = 400; x[2] = 700; x[3] = 1000; x[4] = 1300; x[5] = 1600;
        y[0] = 400; y[1] = 450; y[2] = 350; y[3] = 600; y[4] = 400; y[5] = 650;

        ArrayList<Block> blocks = new ArrayList<>();
        int len = AppConstants.getBitmapBank().getBlocksNum();

        Block block_start = new Block(BlockType.START, x[0], y[0]);
        blocks.add(0, block_start);

        for (int i = 1; i < len-1; i++) {
            Block block = new Block(BlockType.DESTROYABLE, x[i], y[i]);
            blocks.add(i, block);
        }

        Block block_end = new Block(BlockType.END, x[len-1], y[len-1]);
        blocks.add(len-1, block_end);
        return blocks;
    }

    public static int getBlockNum(int level) {
        if (level == 1) {
            return x.length;
        }
        else
            return x.length;
    }
}
