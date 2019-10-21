package edu.amd.spbstu.jumper;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;

    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    private float volume = 1f;
    private static int jumpSound;
    private static int clickSound;
    private static int fallingSound;
    private static int punchSound;
    private static int winSound;

    private boolean isBackSoundPlaying;

    public SoundPlayer(Context context) {

        // SoundPool is deprecated in API level 21. (Lollipop)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }


        jumpSound = soundPool.load(context, R.raw.jump, 1);
        clickSound = soundPool.load(context, R.raw.click, 1);
        fallingSound = soundPool.load(context, R.raw.falling, 1);
        punchSound = soundPool.load(context, R.raw.punch, 1);
        winSound = soundPool.load(context, R.raw.level_winner, 1);

        mediaPlayer = MediaPlayer.create(context, R.raw.menu_music);
        mediaPlayer.setLooping(true);
        isBackSoundPlaying = false;

    }

    public void playJumpSound() {
        if (soundPool == null)
            return;
        soundPool.play(jumpSound, volume, volume, 1, 0, 1.0f);
    }

    public void playClickSound() {
        if (soundPool == null)
            return;
        soundPool.play(clickSound, volume, volume, 1, 0, 1.0f);
    }

    public void playFallingSound() {
        if (soundPool == null)
            return;
        soundPool.play(fallingSound, volume * 0.5f, volume * 0.5f, 1, 0, 1.0f);
    }

    public void playPunchSound() {
        if (soundPool == null)
            return;
        soundPool.play(punchSound, volume, volume, 1, 0, 1.0f);
    }

    public void playWinSound() {
        if (soundPool == null)
            return;
        soundPool.play(winSound, volume * 0.75f, volume * 0.75f, 1, 0, 1.0f);
    }


    public void playBackSound() {
        if (soundPool == null)
            return;
        if (!isBackSoundPlaying) {
            System.out.println("PLAY");

            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            isBackSoundPlaying = true;
        }
    }

    public void stopBackSound() {
        if (soundPool == null)
            return;
        if (isBackSoundPlaying) {
            System.out.println("STOP");
            mediaPlayer.stop();
            isBackSoundPlaying = false;
        }
    }

    public void resumeBackSound() {
        if (soundPool == null)
            return;
        if (!isBackSoundPlaying) {
            System.out.println("RESUME");

            mediaPlayer.start();
            isBackSoundPlaying = true;
        }
    }

    public void pauseBackSound() {
        if (soundPool == null)
            return;
        if (isBackSoundPlaying) {
            System.out.println("PAUSE");

            mediaPlayer.pause();
            isBackSoundPlaying = false;
        }
    }

    public void soundOff() {
        if (mediaPlayer == null)
            return;
        volume = 0f;
        mediaPlayer.setVolume(0f, 0f);
    }

    public void soundOn() {
        if (mediaPlayer == null)
            return;
        volume = 1f;
        mediaPlayer.setVolume(1f, 1f);
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
