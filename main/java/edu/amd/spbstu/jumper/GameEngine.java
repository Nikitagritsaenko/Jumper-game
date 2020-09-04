package grits.jumper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Locale;

import jumper.R;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class GameEngine {

    private static final double DELAY_SEC = 0.01;

    private BackgroundImage backgroundImage;
    private ArrayList<Block> blocks;
    private ArrayList<BlockType> blockTypes = new ArrayList<>();
    private Pair<Block, Block> portal = null;
    private LevelGenerator levelGenerator = new LevelGenerator();
    private GameState gameState = GameState.NOT_STARTED;
    private Player player = new Player();
    private SoundPlayer soundPlayer = AppConstants.getSoundPlayer();

    private static boolean isMovingLeft = false;
    private static boolean isMovingRight = false;
    private static boolean isSoundOn = true;
    private double totalMoved = 0.0;
    private double jumpStep;
    private static boolean hasTouchedBlock = false;
    private int startIdx, endIdx;
    private int lastBlockIdx = 0;
    private double jumpNum = 6.0;
    private static double delay = DELAY_SEC;
    private String levelText;
    private Paint levelPainter = new Paint();

    public Context context;

    static void setMovingLeft(boolean movingLeft) {

        if (hasTouchedBlock) {
            isMovingLeft = movingLeft;
            hasTouchedBlock = false;
        }
    }

    static void setMovingRight(boolean movingRight) {

        if (hasTouchedBlock) {
            isMovingRight = movingRight;
            hasTouchedBlock = false;
        }
    }

    GameEngine(Context context) {

        this.context = context;
        levelText = context.getString(R.string.level);
        levelPainter.setColor(context.getResources().getColor(R.color.text_black));
        levelPainter.setAntiAlias(true);
        levelPainter.setStyle(Paint.Style.FILL);
        String strLang = Locale.getDefault().getDisplayLanguage();

        float size;
        if (strLang.equalsIgnoreCase("english")) {
            size = AppConstants.getExitW() / 2;
        }
        else {
            size = AppConstants.getExitW() / 2.5f;
        }
        levelPainter.setTextSize(size);
        levelPainter.setTextAlign(Paint.Align.CENTER);

        saveUserProgress();
    }

    void updateAndDrawBackgroundImage(Canvas canvas) {

        canvas.drawBitmap(
                AppConstants.getBitmapBank().getBackground(),
                backgroundImage.getX(),
                backgroundImage.getY(),
                null
        );

        canvas.drawBitmap(
                AppConstants.getBitmapBank().getRestart(),
                AppConstants.restartX,
                AppConstants.restartY,
                null
        );

        canvas.drawBitmap(AppConstants.getBitmapBank().getExit(),
                AppConstants.exitX,
                AppConstants.exitY,
                null
        );

        String text = levelText + " " +
                AppConstants.getCurrLevel() +
                " / " +
                AppConstants.getNumLevels();

        canvas.drawText(
                text,
                AppConstants.getExitX() + AppConstants.getExitW() * 2.4f,
                AppConstants.getExitY() + AppConstants.getExitH() / 2,
                levelPainter
        );

        if (gameState != GameState.PAUSED) {
            canvas.drawBitmap(
                    AppConstants.getBitmapBank().getPause(),
                    AppConstants.pauseX,
                    AppConstants.pauseY,
                    null
            );
        }
        else {
            canvas.drawBitmap(
                    AppConstants.getBitmapBank().getPlay(),
                    AppConstants.pauseX,
                    AppConstants.pauseY,
                    null
            );
        }

        if (isSoundOn) {
            canvas.drawBitmap(
                    AppConstants.getBitmapBank().getSoundOn(),
                    AppConstants.soundX,
                    AppConstants.soundY,
                    null
            );
        }
        else {
            canvas.drawBitmap(
                    AppConstants.getBitmapBank().getSoundOff(),
                    AppConstants.soundX,
                    AppConstants.soundY,
                    null
            );
        }
    }

    void updateBlocks(Canvas canvas) {

        if (blockTypes == null) {
            return;
        }

        int len = AppConstants.getBitmapBank().getBlocksNum();

        if (AppConstants.getBitmapBank().getBlocks().length != len) {
            AppConstants.getBitmapBank().setBlocks(null);
            AppConstants.getBitmapBank().initBlocks(blockTypes);
        }

        int portalLeft = 0, portalRight = 0;

        for (int i = 0; i < len; i++) {

            if (portalLeft != 0 && portalRight != 0) {
                portal = new Pair<>(blocks.get(portalLeft), blocks.get(portalRight));
            }

            Block b = blocks.get(i);
            if (b.getType() == BlockType.EMPTY) {
                continue;
            }

            if (b.getType() == BlockType.END) {
                canvas.drawBitmap(
                        AppConstants.getBitmapBank().getBlocks()[i],
                        b.getX(),
                        b.getY() - AppConstants.getPlayerH(),
                        null
                );
            }
            else if (b.getType() == BlockType.PORTAL) {
                if (portalLeft == 0) {
                    portalLeft = i;
                }
                else {
                    portalRight = i;
                }

                Paint paint = new Paint();
                if (b.getDegree() == 0) {
                    b.setAlpha(b.getAlpha() - AppConstants.PORTAL_DISCOLORATION_SPEED);
                }

                int alpha = b.getAlpha();

                if (alpha > 0) {
                    paint.setAlpha(alpha);

                    canvas.drawBitmap(
                            AppConstants.getBitmapBank().getBlocks()[i],
                            blocks.get(i).getX() - AppConstants.getBlockH() / 2,
                            blocks.get(i).getY() - AppConstants.getBlockW() / 2,
                            paint
                    );
                }
            }
            else {
                Paint paint = new Paint();
                if (b.getDegree() == 0) {
                    b.setAlpha(b.getAlpha() - AppConstants.BLOCK_DISCOLORATION_SPEED);
                }

                int alpha = b.getAlpha();

                if (alpha > 0) {
                    paint.setAlpha(b.getAlpha());

                    int shiftX = 0;

                    if (b.getType() == BlockType.DESTROYABLE_2 && b.getDegree() != 2) {
                        // centering the position of the double block when it's small
                        shiftX = (int)(AppConstants.getBlockW() * (1.0 - AppConstants.SQUEEZE_BLOCK_COEFFICIENT) / 2);
                    }

                    canvas.drawBitmap(
                            AppConstants.getBitmapBank().getBlocks()[i],
                            blocks.get(i).getX() + shiftX,
                            blocks.get(i).getY(),
                            paint
                    );
                }
            }
        }
    }

    void updateAndDrawPlayer(Canvas canvas) {

        if (delay >= 0) {
            delay -= 1.0 / 60.0;
        }

        jumpNum = 5.0;

        jumpStep = AppConstants.getGridStepX() / jumpNum;

        if (gameState != GameState.PAUSED) {

            player.setVelocity(player.getVelocity() + AppConstants.getGravity());
            player.setY(player.getY() + player.getVelocity());

            if (gameState != GameState.GAME_OVER) {
                if (isMovingRight) {
                    if (totalMoved >= AppConstants.getGridStepX() / jumpStep) {
                        isMovingRight = false;
                        totalMoved = 0.0;
                    }
                    else {
                        player.setX(player.getX() + jumpStep);
                        totalMoved++;
                    }
                }

                if (isMovingLeft) {
                    if (totalMoved >= AppConstants.getGridStepX() / jumpStep) {
                        isMovingLeft = false;
                        totalMoved = 0.0;
                    }
                    else {
                        player.setX(player.getX() - jumpStep);
                        totalMoved++;
                    }
                }
            }
            else {
                AppConstants.getBitmapBank().rotatePlayer(10f);
            }
        }

        canvas.drawBitmap(
                AppConstants.getBitmapBank().getPlayer(),
                (int)player.getX(),
                (int)player.getY(),
                null
        );
    }

    void updateCollision() {

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

        if (gameState == GameState.PAUSED) {
            return;
        }

        double x = player.getX();
        double y = player.getY();

        for (Block b : blocks) {
            if (b.getDegree() == 0) {
                continue;
            }

            /* portals */
            if (b.getType() == BlockType.PORTAL && b.getDegree() > 0) {
                double sy = ((b.getY() + AppConstants.getBlockH()) - (y + AppConstants.getPlayerH()));
                double sx = ((b.getX() + AppConstants.getBlockW()) - (x + AppConstants.getPlayerW()));
                double r = sqrt(sx * sx + sy * sy);

                if (r < AppConstants.getPlayerH()) {
                    
                    Block p;
                    if (portal.first == b) {
                        p = portal.second;
                    }
                    else {
                        p = portal.first;
                    }
                    
                    player.setX(p.getX());
                    player.setY(p.getY());
                    player.setVelocity(0);
                    soundPlayer.playPortalSound();
                    portal.first.decreaseDegree();
                    portal.second.decreaseDegree();
                    totalMoved = 0.0;
                    isMovingLeft = false;
                    isMovingRight = false;
                }

                continue;
            }

            double sy = b.getY() - y;
            double sx = b.getX() - x;
            double s = sqrt(sx * sx + sy * sy); // distance between player's top left and block top left

            /* Check if player crushed */
            if (sx > 1 && s < AppConstants.getPlayerW() && abs(sy) < 0.7 * AppConstants.getBlockH()) {
                isMovingRight = false;
                totalMoved = 0.0;
                player.setX(b.getX() - AppConstants.getPlayerW());
                soundPlayer.playPunchSound();
                //soundPlayer.playFallingSound();
                delay = DELAY_SEC;
                gameState = GameState.GAME_OVER;
            }
            else if (sx < -1 && s < AppConstants.getPlayerW() && abs(sy) < 0.7 * AppConstants.getBlockH()) {
                isMovingLeft = false;
                totalMoved = 0.0;
                player.setX(b.getX() + AppConstants.getPlayerW());
                soundPlayer.playPunchSound();
                //soundPlayer.playFallingSound();
                delay = DELAY_SEC;
                gameState = GameState.GAME_OVER;
            }
            /* check if player land on block */
            else if (sy >= 0 && s < 0.9 * AppConstants.getPlayerH()) {
                player.setVelocity(-b.getInitVelocity());
                player.setY(player.getY() + player.getVelocity());

                /* springs */
                if (b.getType() == BlockType.SPRING) {
                    soundPlayer.playSpringSound();
                }
                else {
                    soundPlayer.playJumpSound();
                }
                hasTouchedBlock = true;

                /* check win condition */
                int lastBlockIdxTmp = lastBlockIdx;
                lastBlockIdx = b.getIdx();
                if (b.getType() == BlockType.END && getBlocksAlive().size() == 2) {
                    player.setY(b.getY() - AppConstants.getPlayerH());
                    soundPlayer.playWinSound();
                    loadNextLevel();
                }

                if (b.getType() != BlockType.DESTROYABLE_2) {
                    b.decreaseDegree();
                }
                else {
                    /* double blocks */
                    if (lastBlockIdxTmp == b.getIdx()) {
                        int pos = b.getPos();
                        Bitmap bitmap = AppConstants.getBitmapBank().getBlocks()[pos];

                        if (bitmap.getWidth() < AppConstants.getBlockW()) {
                            AppConstants.getBitmapBank().maximizeBlock(b.getPos());
                            b.increaseDegree();
                        } else {
                            AppConstants.getBitmapBank().minimizeBlock(b.getPos());
                            b.decreaseDegree();
                        }
                    } else {
                        AppConstants.getBitmapBank().minimizeBlock(b.getPos());
                        b.decreaseDegree();
                    }
                }
            } else if (sx <= 1 && sy < 0 && s < 0.7 * AppConstants.getPlayerH()) {
                /* check crush to ceil */
                player.setVelocity(0);
                player.setY(b.getY() + AppConstants.getBlockH());
            }
        }

    }

    private void saveUserProgress() {

        int level = loadUserProgress();

        if (level >= AppConstants.getCurrLevel()) {
            return;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Level", AppConstants.getCurrLevel());
        editor.apply();
    }

    int loadUserProgress() {
        // TEMPORARY!
        return AppConstants.getNumLevels();

        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int curr_level_progress = preferences.getInt("Level", -1);
        if (curr_level_progress == 0)
            return 1;
        return curr_level_progress;*/
    }


    private void loadNextLevel() {

        AppConstants.setCurrLevel(AppConstants.getCurrLevel() + 1);
        saveUserProgress();
        restartGame();
    }

    private Block getBlockByIndex(int index) {

        for (Block b: blocks) {
            if (b.getIdx() == index) {
                return b;
            }
        }

        return null;
    }

    void restartGame() {

        AppConstants.getSoundPlayer().resumeBackSound();

        portal = null;
        AppConstants.getBitmapBank().portalIndices = null;
        AppConstants.getBitmapBank().portalIndices = new ArrayList<>();

        blocks = levelGenerator.generateBlocks(AppConstants.getCurrLevel());
        blockTypes.clear();
        blockTypes = new ArrayList<>();
        for (Block b: blocks) {
            blockTypes.add(b.getType());
        }

        AppConstants.getBitmapBank().initBlocks(blockTypes);

        jumpStep = AppConstants.getGridStepX() / jumpNum;

        backgroundImage = new BackgroundImage();

        // check flip to run again correctly
        if (player.isFlipped())  {
            AppConstants.getBitmapBank().flipPlayer();
            player.setFlipped(false);
        }

        player.setX(blocks.get(0).getX());
        player.setY(blocks.get(0).getY() - AppConstants.getGridStepY());

        player.setVelocity(0.0);
        isMovingLeft = false;
        isMovingRight = false;
        totalMoved = 0.0;
        hasTouchedBlock = false;
        gameState = GameState.PLAYING;
        lastBlockIdx = 0;
        delay = DELAY_SEC;
    }

    void pauseGame() {

        soundPlayer.pauseBackSound();
        gameState = GameState.PAUSED;
    }

    void resumeGame() {

        soundPlayer.resumeBackSound();
        gameState = GameState.PLAYING;
    }

    Player getPlayer() {
        return player;
    }

    GameState getGameState() {
        return gameState;
    }

    ArrayList<Block> getBlocksAlive() {

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
            if (b.getIdx() == lastBlockIdx || b.getIdx() == endIdx || b.getType() == BlockType.PORTAL) {
                continue;
            }

            if (b.getDegree() > 0 && b.getType() != BlockType.START) {
                blocksAlive.add(b);
                if (b.getDegree() == 2)
                    blocksAlive.add(b);
            }
        }

        blocksAlive.add(blocksAlive.size(), blocks.get(blocks.size() - 1));

        return blocksAlive;
    }

    boolean IsSoundOn() {
        return isSoundOn;
    }

    void setSoundOn(boolean isSoundOn) {
        GameEngine.isSoundOn = isSoundOn;
    }

    void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
        if (lastBlockIdx == 0) {
            lastBlockIdx = startIdx;
        }
    }

    void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }

    int getStartIdx() {
        return startIdx;
    }

    int getEndIdx() {
        return endIdx;
    }
}
