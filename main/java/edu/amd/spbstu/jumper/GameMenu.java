package edu.amd.spbstu.jumper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class GameMenu extends AppCompatActivity {
    private long backPressedTime;
    private static Toast backToast;

    private static void cancelToast() {
        if (backToast != null) {
            backToast.cancel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonStart = (Button)findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cancelToast();
                    Intent intent = new Intent(GameMenu.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {

                }
            }
        });

        Window w = getWindow();

        AppConstants.initialization(this.getApplicationContext());

        // fullscreen
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

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

    //system back button
    @Override
    public void onBackPressed() {
        long timeToPressAgain = 2000;
        if (backPressedTime + timeToPressAgain > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            Context context = getBaseContext();
            backToast = Toast.makeText(context, context.getString(R.string.on_back_button_pressed), Toast.LENGTH_SHORT);
            backToast.setDuration(Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        SoundPlayer sp = AppConstants.getSoundPlayer();
        if (sp != null) {
            //sp.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
