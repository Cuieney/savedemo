package com.example.cuieney.savedemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.feetsdk.android.FeetSdk;
import com.feetsdk.android.common.utils.SystemInfoUtil;
import com.feetsdk.android.feetsdk.ui.FwController;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private FwController feetUiController;
    private Button add;
    private ToggleButton button;
    private boolean checked;
    private TextView textView;
    private View close;
    private View play;
    private View pause;
    private AudioManager mAudioMgr;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = ((Button) findViewById(R.id.add));
        button = ((ToggleButton) findViewById(R.id.toggle));
        textView = (TextView) findViewById(R.id.btn);
        close = findViewById(R.id.close);
        play = findViewById(R.id.play_msc);
        pause = findViewById(R.id.pause_msc);

        feetUiController = FeetSdk.getFeetUiController();
        feetUiController.setLocation(600);

        button.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checked = isChecked;
            textView.setText(isChecked + "");
            feetUiController.setAutoBpm(isChecked, MainActivity.this);
        });
        button.setChecked(true);
        add.setOnClickListener(v -> {
            int bpm = Integer.parseInt(add.getText().toString());
            if (bpm > 200) {
                bpm = 120;
            } else {
                bpm += 5;
            }
            add.setText(bpm + "");
            if (!checked) {
                feetUiController.setBpm(bpm);
            }
        });

        add.setOnClickListener(v -> {
            int bpm = Integer.parseInt(add.getText().toString());
            if (bpm > 200) {
                bpm = 120;
            } else {
                bpm += 5;
            }
            add.setText(bpm + "");
            if (!checked) {
                feetUiController.setBpm(bpm);
            }
        });

        mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                Log.i(TAG, "onAudioFocusChange: "+focusChange);
            }
        };

        textView.setOnClickListener(v -> feetUiController.show(MainActivity.this));

        close.setOnClickListener(v -> feetUiController.remove());

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource("http://mp3.paohaile.com/songs/28183612.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                requestAudioFocus();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                abandonAudioFocus();
            }
        });
    }

    private void requestAudioFocus() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){
            return;
        }
        if (mAudioMgr == null)
            mAudioMgr = (AudioManager)
                    getSystemService(Context.AUDIO_SERVICE);
        if (mAudioMgr != null) {
            Log.i(TAG, "Request audio focus");
            int ret = mAudioMgr.requestAudioFocus(mAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (ret != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.i(TAG, "request audio focus fail. " + ret);
            }
        }

    }

    private void abandonAudioFocus() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1){
            return;
        }
        if (mAudioMgr != null) {

            Log.i(TAG, "Abandon audio focus");


            mAudioMgr.abandonAudioFocus(mAudioFocusChangeListener);

            mAudioMgr = null;
        }
    }
}
