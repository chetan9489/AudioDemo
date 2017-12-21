package com.example.chetanchauhan.audiodemo;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;
    AudioManager audioManager;
    public void play(View view)
    {
        if(!mplayer.isPlaying())
            mplayer.start();
    }

    public void pause(View view)
    {
        if(mplayer.isPlaying())
            mplayer.pause();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mplayer=MediaPlayer.create(this,R.raw.song);
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volumeController=(SeekBar)findViewById(R.id.seekBar);
        final SeekBar audioController=(SeekBar)findViewById(R.id.seekBar2);

        int maxSong=mplayer.getDuration();
        int curSong=mplayer.getCurrentPosition();

        audioController.setMax(maxSong);
        audioController.setProgress(curSong);

        volumeController.setMax(maxVolume);
        volumeController.setProgress(curVolume);


        volumeController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Seekbar value ",Integer.toString(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }
        });

        audioController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mplayer.start();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)
                    mplayer.seekTo(i);
            }
        });


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioController.setProgress(mplayer.getCurrentPosition());
            }
        },0,100);
    }
}
