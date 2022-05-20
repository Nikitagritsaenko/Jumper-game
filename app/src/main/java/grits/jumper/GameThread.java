package grits.jumper;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private boolean isRunning;

    GameThread(SurfaceHolder holder) {

        this.surfaceHolder = holder;
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = SystemClock.uptimeMillis();
            Canvas canvas = surfaceHolder.lockCanvas(null);

            if (canvas != null) {
                synchronized (surfaceHolder) {
                    AppConstants.getGameEngine().updateAndDrawBackgroundImage(canvas);
                    AppConstants.getGameEngine().updateBlocks(canvas);
                    AppConstants.getGameEngine().updateAndDrawPlayer(canvas);
                    AppConstants.getGameEngine().updateCollision();

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            long loopTime = SystemClock.uptimeMillis() - startTime;
            long delay = 33;

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

    boolean isRunning() {
        return isRunning;
    }

    void setRunning(boolean state) {
        isRunning = state;
    }
}
