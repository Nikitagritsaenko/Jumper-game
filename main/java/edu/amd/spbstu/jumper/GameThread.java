package edu.amd.spbstu.jumper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private long startTime, loopTime;
    private long delay = 33;

    private Paint levelText = new Paint();


    public GameThread(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        isRunning = true;
    }

    @Override
    public void run() {

        while (isRunning) {
            startTime = SystemClock.uptimeMillis();
            Canvas canvas = surfaceHolder.lockCanvas(null);
            if (canvas != null) {
                synchronized (surfaceHolder) {
                    AppConstants.getGameEngine().updateAndDrawBackgroundImage(canvas);
                    AppConstants.getGameEngine().updateBlocks(canvas);
                    AppConstants.getGameEngine().updateAndDrawPlayer(canvas);
                    AppConstants.getGameEngine().updateCollision();
                    AppConstants.getGameEngine().updateAutoPlaying();

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            loopTime = SystemClock.uptimeMillis() - startTime;
            if (loopTime < delay) {
                try {
                    Thread.sleep(delay - loopTime);
                }
                catch (InterruptedException e) {
                    Log.e("Interrupted", "Interrupted while sleeping");
                }
            }

        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean state) {
        isRunning = state;
    }

}
