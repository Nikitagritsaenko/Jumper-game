package grits.jumper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import jumper.R;

class BitmapBank {
    
    private static final String MODE = "arctic_";

    private Bitmap background;
    private Bitmap player;
    private Bitmap pause;
    private Bitmap restart;
    private Bitmap play;
    private Bitmap exit;
    private Bitmap soundOn;
    private Bitmap soundOff;
    private Bitmap portal;
    private Bitmap blockSimple, blockSpecial, blockDouble, blockFinish, blockSpring;

    private int numBlocks;
    private int numBlocksX, numBlocksY;
    
    private Bitmap[] blocks;
    private boolean[] isBlockMinimized;

    ArrayList<Integer> portalIndices = new ArrayList<>();
    
    private void maximizeAllBlocks() {
        
        for (int i = 0; i < blocks.length; i++) {
            maximizeBlock(i);
        }
    }
    
    void maximizeBlock(int i) {
        
        if (!isBlockMinimized[i]) {
            return;
        }

        blocks[i] = Bitmap.createScaledBitmap(
                blocks[i],
                AppConstants.getBlockW(),
                AppConstants.getBlockH(),
                false
        );

        isBlockMinimized[i] = false;
    }

    void minimizeBlock(int i) {

        if (isBlockMinimized[i]) {
            return;
        }

        blocks[i] = Bitmap.createScaledBitmap(
                blocks[i],
                (int)(blocks[i].getWidth() * AppConstants.SQUEEZE_BLOCK_COEFFICIENT),
                (int)(blocks[i].getHeight() * AppConstants.SQUEEZE_BLOCK_COEFFICIENT),
                false
        );

        isBlockMinimized[i] = true;
    }

    private static Bitmap getBitmap(String name) {
        
        Context context = MyApp.getContext();
        
        int resourceId = context.getResources().getIdentifier(
                name, 
                "drawable", 
                MyApp.getContext().getPackageName()
        );
        
        Drawable drawable = context.getResources().getDrawable(resourceId);
        
        if (drawable instanceof BitmapDrawable) {
            
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        Bitmap bitmap;

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            // Single color bitmap will be created of 1x1 pixel
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); 
        } 
        else {
            bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(), 
                    drawable.getIntrinsicHeight(), 
                    Bitmap.Config.ARGB_8888
            );
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        
        return bitmap;
    }

    private Bitmap setImageSize(Bitmap bitmap, int width, int height) {
        
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    private Bitmap scaleImage(Bitmap bitmap) {
        
        return Bitmap.createScaledBitmap(
                bitmap, 
                AppConstants.getScreenWidth(), 
                AppConstants.getScreenHeight(), 
                false
        );
    }

    void scalePlayer(double scale) {
        
        player = getBitmap(MODE + "player");
        player = Bitmap.createScaledBitmap(
                player, 
                (int)scale, 
                (int)scale, 
                false
        );
        
        AppConstants.setPlayerW(player.getWidth());
        AppConstants.setPlayerH(player.getHeight());
    }


    void initBlocks(ArrayList<BlockType> blockTypes) {
        
        if (blocks != null) {
            AppConstants.getBitmapBank().maximizeAllBlocks();
        }
        else {
            blocks = new Bitmap[numBlocks];

            int startIdx = AppConstants.getGameEngine().getStartIdx();
            int endIdx   = AppConstants.getGameEngine().getEndIdx();

            isBlockMinimized = new boolean[numBlocks];

            blocks[0] = blockSpecial;
            blocks[0] = setImageSize(blocks[0], player.getWidth(), player.getHeight());

            int j = 1;

            for (int i = 0; i < numBlocks; i++) {
                if (i != startIdx && i != endIdx) {
                    if (blockTypes.get(j) == BlockType.DESTROYABLE_2) {
                        blocks[j] = blockDouble;
                    }
                    else if (blockTypes.get(j) == BlockType.SPRING) {
                        blocks[j] = blockSpring;
                    }
                    else if (blockTypes.get(j) == BlockType.PORTAL) {
                        blocks[j] = portal;
                        portalIndices.add(j);
                    }
                    else {
                        blocks[j] = blockSimple;
                    }

                    boolean isPortal = false;
                    for (int idx: portalIndices) {
                        if (idx == j) {
                            isPortal = true;
                        }
                    }

                    if (isPortal) {
                        blocks[j] = setImageSize(
                                blocks[j],
                                player.getWidth() * 2,
                                player.getHeight() * 2
                        );
                    }
                    else {
                        blocks[j] = setImageSize(
                                blocks[j],
                                player.getWidth(),
                                player.getHeight()
                        );
                    }

                    j++;
                }
            }

            blocks[numBlocks - 1] = blockFinish;
            blocks[numBlocks - 1] = setImageSize(
                    blocks[numBlocks - 1],
                    player.getWidth(),
                    player.getHeight() * 2
            );

            AppConstants.setBlockH(blocks[0].getHeight());
            AppConstants.setBlockW(blocks[0].getWidth());
        }
    }

    BitmapBank(Resources res) {

        BitmapFactory.Options optionsGameObjects = new BitmapFactory.Options();
        optionsGameObjects.inSampleSize = 8;

        BitmapFactory.Options optionsGameButtons = new BitmapFactory.Options();
        optionsGameButtons.inSampleSize = 1;

        BitmapFactory.Options optionsGameBackground = new BitmapFactory.Options();
        optionsGameBackground.inSampleSize = 1;

        pause = BitmapFactory.decodeResource(res, R.drawable.pause, optionsGameButtons);
        restart = BitmapFactory.decodeResource(res, R.drawable.restart, optionsGameButtons);
        restart = setImageSize(restart, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        play = BitmapFactory.decodeResource(res, R.drawable.play, optionsGameButtons);
        play = setImageSize(play, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOn = BitmapFactory.decodeResource(res, R.drawable.audio, optionsGameButtons);
        soundOn = setImageSize(soundOn, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        soundOff = BitmapFactory.decodeResource(res, R.drawable.no_audio, optionsGameButtons);
        soundOff = setImageSize(soundOff, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));
        exit = BitmapFactory.decodeResource(res, R.drawable.exit, optionsGameButtons);
        exit = setImageSize(exit, (int)(1.0 * pause.getWidth()), (int)(1.0 * pause.getHeight()));

        background = getBitmap(MODE + "background");
        background = scaleImage(background);
        player = getBitmap(MODE + "player");
        blockSimple = getBitmap(MODE + "block");
        blockSpecial = getBitmap(MODE + "block_special");
        blockDouble = getBitmap(MODE + "block_double");
        blockFinish = getBitmap(MODE + "block_finish");
        blockSpring = getBitmap(MODE + "block_spring");
        portal = getBitmap(MODE + "portal");

        AppConstants.exitH = exit.getHeight();
        AppConstants.exitW = exit.getWidth();
        AppConstants.exitX = AppConstants.getScreenWidth() - 4 * pause.getWidth();
        AppConstants.exitY = 0;

        AppConstants.pauseH = pause.getHeight();
        AppConstants.pauseW = pause.getWidth();
        AppConstants.pauseX = AppConstants.getScreenWidth() - 5 * pause.getWidth();
        AppConstants.pauseY = 0;

        AppConstants.restartH = restart.getHeight();
        AppConstants.restartW = restart.getWidth();
        AppConstants.restartX = AppConstants.pauseX - restart.getWidth();
        AppConstants.restartY = 0;

        AppConstants.soundH = soundOn.getHeight();
        AppConstants.soundW = soundOn.getWidth();
        AppConstants.soundX = AppConstants.restartX - soundOn.getWidth();
        AppConstants.soundY = 0;
    }

    void flipPlayer() {

        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);

        player = Bitmap.createBitmap(
                player, 0, 0, player.getWidth(), player.getHeight(), matrix, true
        );
    }

    void rotatePlayer(float angle) {

        Canvas canvas = new Canvas(player);
        Matrix matrix = new Matrix();
        matrix.setRotate(angle,player.getWidth() / 2,player.getHeight() / 2);

        canvas.drawBitmap(player, matrix, new Paint());
    }

    Bitmap getBackground() {
        return background;
    }

    Bitmap[] getBlocks() { return blocks; }

    void setBlocks(Bitmap[] blocks) {
        this.blocks = blocks;
    }

    Bitmap getPlayer() {
        return player;
    }

    Bitmap getPause() {
        return pause;
    }

    Bitmap getRestart() {
        return restart;
    }

    Bitmap getExit() {
        return exit;
    }

    Bitmap getPlay() {
        return play;
    }

    Bitmap getSoundOn() {
        return soundOn;
    }

    Bitmap getSoundOff() {
        return soundOff;
    }

    int getBlocksNum() {
        return numBlocks;
    }

    int getNumBlocksX() {
        return numBlocksX;
    }

    int getNumBlocksY() {
        return numBlocksY;
    }

    int getNumBlocks() {
        return numBlocks;
    }

    void setNumBlocksX(int numBlocksX) {
        this.numBlocksX = numBlocksX;
    }

    void setNumBlocksY(int numBlocksY) {
        this.numBlocksY = numBlocksY;
    }

    void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }
}

