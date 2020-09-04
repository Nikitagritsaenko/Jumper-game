package grits.jumper;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameThread gameThread;

    public GameView(Context context) {
        super(context);
        initView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (!gameThread.isRunning()) {
            gameThread = new GameThread(holder);
        }
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if (gameThread.isRunning()) {
            gameThread.setRunning(false);
            boolean retry = true;
            while (retry) {
                try {
                    gameThread.join();
                    retry = false;
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    void initView() {

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(holder);
    }
}
