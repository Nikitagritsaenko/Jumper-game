package edu.amd.spbstu.jumper;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class AppConstants {
    static GameEngine gameEngine;
    static BitmapBank bitmapBank;
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;
    static int gravity = 3;
    static int velocity = 0;
    static int jumpVelocity = -40;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void initialization(Context context) {
        setScreenSize(context);
        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine();
    }

    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setGravity(int gravity) {

        this.gravity = gravity;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getGravity() {
        return gravity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void setScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;
    }
}
