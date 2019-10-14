package edu.amd.spbstu.jumper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Level extends AppCompatActivity implements GestureDetector.OnGestureListener {
    GameView gameView;
    GestureDetector gDetector;
    SoundPlayer soundPlayer;

    private long backPressedTime;
    private long autoplayPressedTime;
    private static Toast backToast;
    private int clickCount = 0;

    public String getLevelText() {
        return getBaseContext().getString(R.string.on_back_button_pressed);
    }

    private static void cancelToast() {
        if (backToast != null) {
            backToast.cancel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("CREATE");

        super.onCreate(savedInstanceState);

        gameView = new GameView(this);


        setContentView(gameView);
        gDetector = new GestureDetector( getBaseContext(), this );

        // fullscreen mode
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //disableActionBar();

        soundPlayer = AppConstants.getSoundPlayer();
        soundPlayer.playBackSound();

        View decorView = getWindow().getDecorView();

        /*decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            //disableActionBar();
                        //}
                    }
                });*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        int level = AppConstants.getGameEngine().loadUserProgress();
        System.out.println("Current user progress is: ");
        System.out.println(level);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            super.onWindowFocusChanged(hasFocus);
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }

    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {
        if (AppConstants.getGameEngine().isAutoPlay()) {
            return false;
        }
        Player player = AppConstants.getGameEngine().getPlayer();
        if (start.getRawX() < finish.getRawX()) {
            player.moveRight();
        } else {
            player.moveLeft();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {


    }

    @Override
    public boolean onScroll(MotionEvent start, MotionEvent finish, float arg2, float arg3) {
        if (AppConstants.getGameEngine().isAutoPlay()) {
            return false;
        }
        Player player = AppConstants.getGameEngine().getPlayer();
        if (start.getRawX() < finish.getRawX()) {
            player.moveRight();
        } else {
            player.moveLeft();
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        float x = arg0.getRawX();
        float y = arg0.getRawY();

        if (x - AppConstants.restartX  < AppConstants.restartW && x - AppConstants.restartX > 0 && abs(AppConstants.restartY - y) < AppConstants.restartH) {
            AppConstants.getGameEngine().restartGame();
        }

        if (x - AppConstants.pauseX  < AppConstants.pauseW && x - AppConstants.pauseX > 0 && abs(AppConstants.pauseY - y) < AppConstants.pauseH) {
            if (AppConstants.getGameEngine().getGameState() != GameStates.PAUSED)
                AppConstants.getGameEngine().pauseGame();
            else
                AppConstants.getGameEngine().resumeGame();
        }

        if (x - AppConstants.soundX  < AppConstants.soundW && x - AppConstants.soundX > 0 && abs(AppConstants.soundY - y) < AppConstants.soundH) {
            if (AppConstants.getGameEngine().IsSoundOn()) {
                AppConstants.getSoundPlayer().soundOff();
                AppConstants.getGameEngine().setSoundOn(false);
            }
            else {
                AppConstants.getSoundPlayer().soundOn();
                AppConstants.getGameEngine().setSoundOn(true);
            }
        }

        if (x - AppConstants.exitX  < AppConstants.exitW && x - AppConstants.exitX > 0 && abs(AppConstants.exitY - y) < AppConstants.exitH) {
            Intent intent = new Intent(Level.this, GameLevels.class);
            startActivity(intent);
            cancelToast();
            finish();
        }

        if (x < AppConstants.pauseW * 4 && y < AppConstants.pauseW * 3) {

            long time = System.currentTimeMillis();
            long timeToPressAgain = 1000;
            if (time - autoplayPressedTime < timeToPressAgain)
                clickCount++;
            else
                if (clickCount == 0)
                    clickCount = 1;
                else
                    clickCount = 0;

            System.out.println(clickCount);

            autoplayPressedTime = time;

            if  (clickCount == 3) {
                runAutoPlay();
                clickCount = 0;
            }
        }


        return false;
    }

    public void runAutoPlay() {
        HamiltonianCycle hc = AppConstants.getGameEngine().getHc();
        GameEngine ge = AppConstants.getGameEngine();


        if (!ge.isAutoPlay()) {
            ge.setPath(hc.findHamiltonianPath());
            ge.setIsAutoPlay(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gDetector.onTouchEvent(me);
    }

    @Override
    protected void onStop() {
        soundPlayer.pauseBackSound();
        AppConstants.getGameEngine().pauseGame();
        super.onStop();
    }

    @Override
    protected void onUserLeaveHint()
    {
        super.onUserLeaveHint();
    }

    @Override
    protected void onResume() {
        soundPlayer.resumeBackSound();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        long timeToPressAgain = 2000;
        if (backPressedTime + timeToPressAgain > System.currentTimeMillis()) {
            cancelToast();
            Intent intent = new Intent(Level.this, GameLevels.class);
            startActivity(intent);
            finish();
        } else {
            Context context = getBaseContext();
            backToast = Toast.makeText(context, context.getString(R.string.on_back_button_pressed_in_game), Toast.LENGTH_SHORT);
            backToast.setDuration(Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            AppConstants.getGameEngine().pauseGame();
            super.onCallStateChanged(state, incomingNumber);
        }
    };

}
