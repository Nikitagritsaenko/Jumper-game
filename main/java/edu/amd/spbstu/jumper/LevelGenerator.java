package edu.amd.spbstu.jumper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import edu.amd.spbstu.jumper.Block;
import edu.amd.spbstu.jumper.BlockType;


public class LevelGenerator {
    private static Integer[] x = {0,0,0};
    private static Integer[] y = {0,0,0};

    public static ArrayList<Block> generateBlocks(int level) {

        if (level == 1) {
           x[0] = 100; x[1] = 400; x[2] = 700;
           y[0] = 400; y[1] = 400; y[2] = 400;
        }
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
}
