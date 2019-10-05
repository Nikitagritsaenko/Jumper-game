package edu.amd.spbstu.jumper;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;

public class GameLevels extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gamelevels);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button button_back = (Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AppConstants.getSoundPlayer().playClickSound();

                    Intent intent = new Intent(GameLevels.this, GameMenu.class);
                    startActivity(intent);
                    finish();

                }catch (Exception e) {

                }
            }
        });

        int levelsUnlocked = AppConstants.getGameEngine().loadUserProgress();
        //int levelsUnlocked = 20;
        for (int i = 1; i <= levelsUnlocked; i++) {
            String id = "textView" + Integer.toString(i);
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            final TextView textView = (TextView) findViewById(resID);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AppConstants.getSoundPlayer().playClickSound();
                        String name = getResources().getResourceName(textView.getId());
                        name = name.split("textView")[1];
                        int level = Integer.parseInt(name);
                        AppConstants.setCurrLevel(level);


                        Intent intent = new Intent(GameLevels.this, Level.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {

                    }
                }
            });
            textView.setText(Integer.toString(i));
        }

        for (int i = levelsUnlocked + 1; i <= AppConstants.getNumLevels(); i++) {
            String id = "textView" + Integer.toString(i);
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            final TextView textView = (TextView) findViewById(resID);
            textView.setBackgroundResource(R.drawable.locked);
            textView.setText("");
        }

    }

    //system back button
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(GameLevels.this, GameMenu.class);
            startActivity(intent);
            finish();
        }
        catch (Exception e) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
