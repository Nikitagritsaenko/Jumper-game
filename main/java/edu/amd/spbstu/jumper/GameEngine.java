package edu.amd.spbstu.jumper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.Math.subtractExact;

public class GameEngine {
    private BackgroundImage backgroundImage;
    private ArrayList<Block> blocks;
    private ArrayList<BlockType> blockTypes = new ArrayList<>();
    private ArrayList<Block> currBlocks;
    private LevelGenerator lg = new LevelGenerator();
    private HamiltonianCycle hc;
    private GameStates gameState = GameStates.NOT_STARTED;
    private static Player player = new Player();
    private SoundPlayer soundPlayer = AppConstants.getSoundPlayer();
    private static boolean isMovingLeft = false;
    private static boolean isMovingRight = false;
    private static boolean isSoundOn = true;
    private boolean isWin = false;
    private double totalMoved = 0.0;
    private double jumpStep;
    private static boolean hasTouchedBlock = false;
    private static boolean isFallingSoundPlayed = false;
    private boolean isAutoPlay = false;
    private int[] path;
    private int startIdx, endIdx;
    private int lastBlockIdx = 0;
    private Block lastBlock;
    private int pos = 0;
    private double jumpNum = 5.0;
    //private static final String FILE_NAME = "/sdcard/Android/data/jumper/progress.txt";
    private static final String FILE_NAME = "progress.txt";
    private static double delay_sec = 0.9;
    private static double delay = delay_sec;

    public String levelText;
    public Paint levelPainter = new Paint();
    public Context context;

    public static void setMovingLeft(boolean movingLeft) {
        if (hasTouchedBlock) {
            isMovingLeft = movingLeft;
            hasTouchedBlock = false;
        }
    }

    public static void setMovingRight(boolean movingRight) {
        if (hasTouchedBlock) {
            isMovingRight = movingRight;
            hasTouchedBlock = false;
        }
    }

    public GameEngine(Context context) {
        this.context = context;
        levelText = context.getString(R.string.level);
        levelPainter.setColor(context.getResources().getColor(R.color.text_black));
        levelPainter.setAntiAlias(true);
        levelPainter.setStyle(Paint.Style.FILL);
        String strLang = Locale.getDefault().getDisplayLanguage();
        float size;
        if (strLang.equalsIgnoreCase("english"))
            size = AppConstants.getExitW() / 2;
        else
            size = AppConstants.getExitW() / 2.5f;
        levelPainter.setTextSize(size);
        levelPainter.setTextAlign(Paint.Align.CENTER);

        saveUserProgress();
    }

    public void updateAndDrawBackgroundImage(Canvas canvas) {
        String text = levelText;
        text += " ";
        text += Integer.toString(AppConstants.getCurrLevel());
        text += " / ";
        text += Integer.toString(AppConstants.getNumLevels());

        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX(), backgroundImage.getY(), null);
        canvas.drawBitmap(AppConstants.getBitmapBank().getRestart(), AppConstants.restartX, AppConstants.restartY, null);
        canvas.drawBitmap(AppConstants.getBitmapBank().getExit(), AppConstants.exitX, AppConstants.exitY, null);
        canvas.drawText(text,  AppConstants.getExitX() + AppConstants.getExitW() * 2.4f,
                AppConstants.getExitY() + AppConstants.getExitH() / 2, levelPainter);

        if (gameState != GameStates.PAUSED)
            canvas.drawBitmap(AppConstants.getBitmapBank().getPause(), AppConstants.pauseX, AppConstants.pauseY, null);
        else
            canvas.drawBitmap(AppConstants.getBitmapBank().getPlay(), AppConstants.pauseX, AppConstants.pauseY, null);

        if (isSoundOn)
            canvas.drawBitmap(AppConstants.getBitmapBank().getSoundOn(), AppConstants.soundX, AppConstants.soundY, null);
        else
            canvas.drawBitmap(AppConstants.getBitmapBank().getSoundOff(), AppConstants.soundX, AppConstants.soundY, null);
    }

    public void updateBlocks(Canvas canvas) {
        if (blockTypes == null)
            return;

        int len = AppConstants.getBitmapBank().getBlocksNum();
        if (AppConstants.getBitmapBank().getBlocks().length != len) {
            AppConstants.getBitmapBank().setBlocks(null);
            AppConstants.getBitmapBank().initBlocks(blockTypes);
        }

        for (int i = 0; i < len; i++) {
            if (blocks.get(i).getDegree() > 0)
                if (blocks.get(i).getType() != BlockType.END)
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBlocks()[i], blocks.get(i).getCoordX(), blocks.get(i).getCoordY(), null);
                else
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBlocks()[i], blocks.get(i).getCoordX(), blocks.get(i).getCoordY() - AppConstants.getPlayerH(), null);
        }
    }

    public void updateAndDrawPlayer(Canvas canvas) {
        if (delay >= 0) {
            delay -= 1.0 / 60.0;
        }
        if (isAutoPlay)
            jumpNum = 2.0;
        else
            jumpNum = 5.0;
        jumpStep = AppConstants.getGridStep() / jumpNum;

        if (gameState != GameStates.PAUSED) {
            player.setVelocity(player.getVelocity() + AppConstants.getGravity());
            player.setY(player.getY() + player.getVelocity());
            if (gameState != GameStates.GAMEOVER) {
                if (isMovingRight) {
                    if (totalMoved >= AppConstants.getGridStep() / jumpStep) {
                        isMovingRight = false;
                        totalMoved = 0.0;
                    } else {
                        player.setX(player.getX() + jumpStep);
                        totalMoved++;
                    }
                }

                if (isMovingLeft) {
                    if (totalMoved >= AppConstants.getGridStep() / jumpStep) {
                        isMovingLeft = false;
                        totalMoved = 0.0;
                    } else {
                        player.setX(player.getX() - jumpStep);
                        totalMoved++;
                    }
                }
            }
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getPlayer(), (int)player.getX(), (int)player.getY(), null);
    }

    public void updateCollision() {
        if (player.getY() < 0) {
            player.setY(0);
            player.setVelocity(0);
            return;
        }

        if (player.getY() > AppConstants.getScreenHeight()) {
            if (delay < 0) {
                restartGame();
                return;
            }
        }

        if (gameState == GameStates.PAUSED) {
            return;
        }

        double x = player.getX();
        double y = player.getY();

        boolean isFallingDown = true;

        for (Block b: blocks) {
            if (b.getDegree() == 0)
                continue;

            double sy = b.getY() - y;
            double sx = b.getX() - x;
            double s = sqrt(sx * sx + sy * sy); // distance between player's top left and block top left

            if (sx <= 1 && sy > 0) {
                // here is a block to fall down
                isFallingDown = false;
            }

            if (sx > 1 && s < AppConstants.getPlayerW() && abs(sy) < 0.7 * AppConstants.getBlockH()) {
                isMovingRight = false;
                totalMoved = 0.0;
                player.setX(b.getX() - AppConstants.getPlayerW());
                soundPlayer.playPunchSound();
                soundPlayer.playFallingSound();
                isFallingSoundPlayed = true;
                delay = delay_sec;
                gameState = GameStates.GAMEOVER;
            }
            else if (sx < -1  && s < AppConstants.getPlayerW() && abs(sy) < 0.7 * AppConstants.getBlockH()) {
                isMovingLeft = false;
                totalMoved = 0.0;
                player.setX(b.getX() + AppConstants.getPlayerW());
                soundPlayer.playPunchSound();
                soundPlayer.playFallingSound();
                isFallingSoundPlayed = true;
                delay = delay_sec;
                gameState = GameStates.GAMEOVER;
            }
            else if (sy >= 0  && s < 0.9 * AppConstants.getPlayerH()) {
                player.setVelocity(-b.getInitVelocity());
                player.setY(player.getY() + player.getVelocity());
                if (b.getType() == BlockType.SPRING)
                    soundPlayer.playSpringSound();
                else
                    soundPlayer.playJumpSound();
                hasTouchedBlock = true;
                lastBlock = b;
                int lastBlockIdxTmp = lastBlockIdx;
                lastBlockIdx = b.getIdx();
                if (b.getType() == BlockType.END && getBlocksAlive().size() == 2) {
                    isAutoPlay = false;
                    player.setY(b.getY() - AppConstants.getPlayerH());
                    isWin = true;
                    soundPlayer.playWinSound();
                    loadNextLevel();
                }
                if (b.getType() != BlockType.DESTROYABLE_2) {
                    b.decreaseDegree();
                }
                else {
                    if (lastBlockIdxTmp == b.getIdx()) {
                        int pos = b.getPos();
                        Bitmap bitmap = AppConstants.getBitmapBank().getBlocks()[pos];

                        if (bitmap.getWidth() < AppConstants.getBlockW()) {
                            AppConstants.getBitmapBank().maximizeBlock(b.getPos());
                            b.increaseDegree();
                        }
                        else {
                            AppConstants.getBitmapBank().minimizeBlock(b.getPos());
                            b.decreaseDegree();
                        }
                    }
                    else {
                        AppConstants.getBitmapBank().minimizeBlock(b.getPos());
                        b.decreaseDegree();
                    }
                }
            }
            else if (sx <= 1 && sy < 0 && s < 0.7 * AppConstants.getPlayerH()) {
                player.setVelocity(0);
                player.setY(b.getY() + AppConstants.getBlockH());
            }
        }

        if (isFallingDown && !isMovingRight && !isMovingLeft && !hasTouchedBlock) {
            gameState = GameStates.GAMEOVER;
        }
    }

    private void makeJump(Block curr_block, Block next_block) {
        int dist = Block.dist(currBlocks, curr_block, next_block);
        if (curr_block.getX() == next_block.getX()) {
            return;
        }

        if (dist == 1)
            player.moveRight();
        else if (dist == -1)
            player.moveLeft();
    }

    public void updateAutoPlaying() {
        // NO AUTOPLAY
        isAutoPlay = false;
        return;
        /*if (!isAutoPlay || isMovingRight || isMovingLeft || !hasTouchedBlock)
            return;

        if (pos + 1 >= path.length) {
            isAutoPlay = false;
            return;
        }

        if (path[pos] == -1) {
            isAutoPlay = false;
            return;
        }

        int curr_idx = path[pos];
        Block curr_block = currBlocks.get(curr_idx);
        int next_idx = path[pos + 1];
        Block next_block;

        if (lastBlockIdx != curr_block.getIdx()) {
            return;
        }

        if (next_idx == -1) {
            isAutoPlay = false;
            return;
        }
        next_block = currBlocks.get(next_idx);

        if (curr_block.getY() > next_block.getY()) {
            // jump up
            System.out.println("J UP");
            if (player.getVelocity() < 0 && player.getY() + AppConstants.getPlayerH() <= next_block.getY()) {
                makeJump(curr_block, next_block);
                pos++;
            }
        }
        else {
            // jump down
            System.out.println("J DOWN");

            if (player.getVelocity() > 0 && player.getY() + 2 * AppConstants.getPlayerH() >= curr_block.getY()) {
                makeJump(curr_block, next_block);
                pos++;
            }
        }*/
    }

    public void saveUserProgress() {
        int level = loadUserProgress();
        if (level >= AppConstants.getCurrLevel())
            return;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Level", AppConstants.getCurrLevel());
        editor.apply();
    }

    public int loadUserProgress() {
        return 40;

        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int curr_level_progress = preferences.getInt("Level", -1);
        if (curr_level_progress == 0)
            return 1;
        return curr_level_progress;*/
    }


    public void loadNextLevel() {

        AppConstants.setCurrLevel(AppConstants.getCurrLevel() + 1);
        saveUserProgress();
        restartGame();
    }

    public Block getBlockByIndex(int index) {
        for (Block b: blocks) {
            if (b.getIdx() == index)
                return b;
        }
        return null;
    }

    public void restartGame() {
        AppConstants.getSoundPlayer().resumeBackSound();

        blocks = lg.generateBlocks(AppConstants.getCurrLevel());
        blockTypes.clear();
        blockTypes = new ArrayList<>();
        for (Block b: blocks) {
            blockTypes.add(b.getType());
        }

        AppConstants.getBitmapBank().initBlocks(blockTypes);

        jumpStep = AppConstants.getGridStep() / jumpNum;

        backgroundImage = new BackgroundImage();

        // check flip to run again correctly
        if (player.isFlipped())  {
            AppConstants.getBitmapBank().flipPlayer();
            player.setFlipped(false);
        }

        player.setX(blocks.get(0).getX());
        player.setY(blocks.get(0).getY() - (int)(AppConstants.getBlockH() * 0.8) - AppConstants.getGridStep());

        player.setVelocity(0.0);
        isMovingLeft = false;
        isMovingRight = false;
        totalMoved = 0.0;
        hasTouchedBlock = false;
        isFallingSoundPlayed = false;
        gameState = GameStates.PLAYING;
        lastBlockIdx = 0;
        pos = 0;
        isAutoPlay = false;
        isWin = false;

        lastBlock = blocks.get(0);
        delay = delay_sec;

        if (hc == null) {
            hc = new HamiltonianCycle();
        }
    }

    public void pauseGame() {
        soundPlayer.pauseBackSound();
        gameState = GameStates.PAUSED;
    }

    public void resumeGame() {
        soundPlayer.resumeBackSound();
        gameState = GameStates.PLAYING;
    }

    public Player getPlayer() {
        return player;
    }

    public GameStates getGameState() {
        return gameState;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Block> getBlocksAlive() {

        ArrayList<Block> blocksAlive = new ArrayList<>();

        if (lastBlockIdx == 0)
            blocksAlive.add(0, blocks.get(0));
        else {
            Block block = getBlockByIndex(lastBlockIdx);
            blocksAlive.add(0, block);

            if (block.getType() == BlockType.DESTROYABLE_2 && block.getDegree() == 2) {
                blocksAlive.add(block);
            }
        }

        for (Block b: blocks) {
            if (b.getIdx() == lastBlockIdx || b.getIdx() == endIdx)
                continue;

            if (b.getDegree() > 0 && b.getType() != BlockType.START) {
                blocksAlive.add(b);
                if (b.getDegree() == 2)
                    blocksAlive.add(b);
            }
        }
        blocksAlive.add(blocksAlive.size(), blocks.get(blocks.size()-1));

        currBlocks = blocksAlive;
        return blocksAlive;
    }

    public boolean IsSoundOn() {
        return isSoundOn;
    }

    public void setSoundOn(boolean isSoundOn) {
        GameEngine.isSoundOn = isSoundOn;
    }

    public int[] getPath() {
        return path;
    }

    public void setIsAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    public int getLastBlockIdx() {
        return lastBlockIdx;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
        if (lastBlockIdx == 0)
            lastBlockIdx = startIdx;
    }

    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }

    public int getStartIdx() {
        return startIdx;
    }

    public int getEndIdx() {
        return endIdx;
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public HamiltonianCycle getHc() {
        return hc;
    }

    public ArrayList<BlockType> getBlockTypes() {
        return blockTypes;
    }
}
