package grits.jumper;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import jumper.R;

class SoundPlayer {
    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    private float volume = 1f;
    private static int jumpSound;
    private static int springSound;
    private static int clickSound;
    private static int fallingSound;
    private static int punchSound;
    private static int winSound;
    private static int portalSound;
    private static int directionReverseSound;
    private static int gravityReverseSound;

    private boolean isBackSoundPlaying;

    SoundPlayer(Context context) {
        // SoundPool is deprecated in API level 21. (Lollipop)
        int SOUND_POOL_MAX = 2;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        }
        else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        jumpSound = soundPool.load(context, R.raw.jump, 1);
        clickSound = soundPool.load(context, R.raw.click, 1);
        fallingSound = soundPool.load(context, R.raw.falling, 1);
        punchSound = soundPool.load(context, R.raw.punch, 1);
        winSound = soundPool.load(context, R.raw.level_winner, 1);
        springSound = soundPool.load(context, R.raw.spring, 1);
        portalSound = soundPool.load(context, R.raw.portal, 1);
        gravityReverseSound = soundPool.load(context, R.raw.gravity_reverse, 1);
        directionReverseSound = soundPool.load(context, R.raw.lr_reverse, 1);

        mediaPlayer = MediaPlayer.create(context, R.raw.menu_music);
        mediaPlayer.setLooping(true);
        isBackSoundPlaying = false;
    }

    void playJumpSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(jumpSound, volume, volume, 1, 0, 1.0f);
    }

    void playSpringSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(springSound, volume, volume, 1, 0, 1.0f);
    }

    void playPortalSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(portalSound, volume * 0.6f, volume * 0.6f, 1, 0, 1.0f);
    }


    void playClickSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(clickSound, volume, volume, 1, 0, 1.0f);
    }

    void playFallingSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(fallingSound, volume * 0.5f, volume * 0.5f, 1, 0, 1.0f);
    }

    void playPunchSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(punchSound, volume, volume, 1, 0, 1.0f);
    }

    void playWinSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(winSound, volume * 0.75f, volume * 0.75f, 1, 0, 1.0f);
    }

    public void playGravityReverseSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(gravityReverseSound, volume, volume, 1, 0, 1.0f);
    }

    public void playDirectionReverseSound() {
        if (soundPool == null) {
            return;
        }
        soundPool.play(directionReverseSound, volume * 0.7f, volume * 0.7f, 1, 0, 1.0f);
    }

    void playBackSound() {
        if (soundPool == null) {
            return;
        }
        if (!isBackSoundPlaying) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            isBackSoundPlaying = true;
        }
    }

    void stopBackSound() {
        if (soundPool == null) {
            return;
        }
        if (isBackSoundPlaying) {
            mediaPlayer.stop();
            isBackSoundPlaying = false;
        }
    }

    void resumeBackSound() {
        if (soundPool == null) {
            return;
        }
        if (!isBackSoundPlaying) {
            mediaPlayer.start();
            isBackSoundPlaying = true;
        }
    }

    void pauseBackSound() {
        if (soundPool == null) {
            return;
        }
        if (isBackSoundPlaying) {
            mediaPlayer.pause();
            isBackSoundPlaying = false;
        }
    }

    void soundOff() {
        if (mediaPlayer == null) {
            return;
        }
        volume = 0f;
        mediaPlayer.setVolume(0f, 0f);
    }

    void soundOn() {
        if (mediaPlayer == null) {
            return;
        }
        volume = 1f;
        mediaPlayer.setVolume(1f, 1f);
    }
}
