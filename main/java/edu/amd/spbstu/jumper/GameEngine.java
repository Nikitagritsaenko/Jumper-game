package edu.amd.spbstu.jumper;

import android.graphics.Canvas;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GameEngine {
    private BackgroundImage backgroundImage;
    private ArrayList<Block> blocks;
    private ArrayList<Block> currBlocks;
    private LevelGenerator lg = new LevelGenerator();
    private HamiltonianCycle hc;
    private GameStates gameState = GameStates.NOT_STARTED;
    private static Player player = new Player();
    private SoundPlayer soundPlayer = AppConstants.getSoundPlayer();
    private static boolean isMovingLeft = false;
    private static boolean isMovingRight = false;
    private static boolean isSoundOn = true;
    private double totalMoved = 0.0;
    private double jumpStep;
    private static boolean hasTouchedBlock = false;
    private static boolean isFallingSoundPlayed = false;
    private boolean isAutoPlay = false;
    private int[] path;
    private int lastBlockIdx = 0;
    private int pos = 0;

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

    public GameEngine() {

    }

    public void updateAndDrawBackgroundImage(Canvas canvas) {
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX(), backgroundImage.getY(), null);
        canvas.drawBitmap(AppConstants.getBitmapBank().getRestart(), AppConstants.restartX, AppConstants.restartY, null);

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
        int len = AppConstants.getBitmapBank().getBlocksNum();
        for (int i = 0; i < len; i++) {
            if (blocks.get(i).getDegree() > 0)
                canvas.drawBitmap(AppConstants.getBitmapBank().getBlocks()[i], blocks.get(i).getX(), blocks.get(i).getY(), null);
        }
    }

    public void updateAndDrawPlayer(Canvas canvas) {
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
        if (player.getY() > AppConstants.getScreenHeight()) {
            if (!isFallingSoundPlayed) {
                soundPlayer.playFallingSound();
                isFallingSoundPlayed = true;
            }
            restartGame();
            return;
        }

        if (gameState == GameStates.PAUSED) {
            return;
        }

        double x = player.getX();
        double y = player.getY();

        boolean isFallingDown = true;
        boolean isPunched = false;

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

            if (sx > 1 && s < AppConstants.getPlayerW() && abs(sy) < 0.8 * AppConstants.getBlockH()) {
                isMovingRight = false;
                totalMoved = 0.0;
                player.setX(b.getX() - AppConstants.getPlayerW());
                soundPlayer.playPunchSound();
                soundPlayer.playFallingSound();
                isFallingSoundPlayed = true;
                gameState = GameStates.GAMEOVER;
                isPunched = true;
            }
            else if (sx < -1  && s < AppConstants.getPlayerW() && abs(sy) < 0.8 * AppConstants.getBlockH()) {
                isMovingLeft = false;
                totalMoved = 0.0;
                player.setX(b.getX() + AppConstants.getPlayerW());
                soundPlayer.playPunchSound();
                soundPlayer.playFallingSound();
                isFallingSoundPlayed = true;
                gameState = GameStates.GAMEOVER;
                isPunched = true;
            }
            else if (sy > 0  && s < 0.8 * AppConstants.getPlayerH()) {
                player.setVelocity(-b.getInitVelocity());
                player.setY(player.getY() + player.getVelocity());
                soundPlayer.playJumpSound();
                hasTouchedBlock = true;
                lastBlockIdx = b.getIdx();
                b.decreaseDegree();
            }
            else if (sx <= 1 && sy < 0 && s < AppConstants.getPlayerH()) {
                player.setVelocity(0);
                player.setY(b.getY() + AppConstants.getBlockH());
            }
        }

        if (isFallingDown && !isMovingRight && !isMovingLeft && !hasTouchedBlock) {
            gameState = GameStates.GAMEOVER;
        }
    }

    private void makeJump(Block curr_block, Block next_block) {
        if (abs(curr_block.getX() - next_block.getX()) > 1) {
            int dist = Block.dist(blocks, curr_block, next_block);

            if (dist == 1)
                player.moveRight();
            else
                player.moveLeft();
            pos++;
        }
        else {
            if (next_block.getDegree() == 0) {
                pos++;
            }
        }

        if (pos == path.length) {
            isAutoPlay = false;
        }
    }

    public void updateAutoPlaying() {
        if (!isAutoPlay || isMovingRight || isMovingLeft || !hasTouchedBlock)
            return;

        if (pos + 1 == path.length) {
            System.out.println("AUTO PLAY HAS ENDED");
            isAutoPlay = false;
            return;
        }

        if (path[pos + 1] == -1) {
            System.out.println("AUTO PLAY HAS ENDED");
            isAutoPlay = false;
            return;
        }

        int curr_idx = path[pos];
        int next_idx = path[pos + 1];
        Block curr_block = currBlocks.get(curr_idx);
        Block next_block = currBlocks.get(next_idx);

        if (curr_block.getY() > next_block.getY() || abs(curr_block.getY() - next_block.getY()) < 1) {
            // jump up
            if (player.getVelocity() >= 1) {
                makeJump(curr_block, next_block);
            }
        }
        else {
            // jump down
            double sy = curr_block.getY() - player.getY();

            if (sy < AppConstants.getPlayerH()) {
                makeJump(curr_block, next_block);
            }
        }

    }

    public void restartGame() {
        blocks = lg.generateBlocks(AppConstants.getCurrLevel());
        jumpStep = AppConstants.getGridStep() / 3.0;

        backgroundImage = new BackgroundImage();
        player.setX(blocks.get(0).getX());
        player.setY(blocks.get(0).getY() - (int)(AppConstants.getBlockH() * 0.8) - AppConstants.getGridStep());
        // check flip to run again correctly
        if (player.isFlipped())  {
            AppConstants.getBitmapBank().flipPlayer();
        }
        player.setFlipped(false);
        player.setVelocity(0.0);
        isMovingLeft = false;
        isMovingRight = false;
        totalMoved = 0.0;
        hasTouchedBlock = false;
        gameState = GameStates.PLAYING;
        lastBlockIdx = 0;
        pos = 0;
        isAutoPlay = false;

        if (hc == null) {
            hc = new HamiltonianCycle();
        }

        path = hc.findHamiltonianPath(lastBlockIdx); ////////
        //isAutoPlay = true;
    }

    public void pauseGame() {
        soundPlayer.pauseMenuSound();
        gameState = GameStates.PAUSED;
    }

    public void resumeGame() {
        soundPlayer.resumeMenuSound();
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
        for (Block b: blocks) {
            if (b.getDegree() > 0)
                blocksAlive.add(b);
        }
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

    public boolean isAutoPlay() {
        return isAutoPlay;
    }
}
