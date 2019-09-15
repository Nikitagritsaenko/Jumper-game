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
    static double gravity = 2.0;
    static int gridStep = 300;
    static int playerH;
    static int playerW;
    static int blockW;
    static int blockH;

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


    public void setGravity(double gravity) {

        this.gravity = gravity;
    }

    public double getGravity() {
        return gravity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void setScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        SCREEN_HEIGHT = metrics.widthPixels; // no mistake here
        SCREEN_WIDTH = metrics.heightPixels;
    }
}
