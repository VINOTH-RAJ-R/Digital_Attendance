package com.example.digitalattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.VideoView;

public class splash_screen extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_splash_screen);
        videoView = findViewById (R.id.intro);
        String videoPath = "android.resource://" + getPackageName () + "/" + R.raw.in;
        Uri uri = Uri.parse (videoPath);
        videoView.setVideoURI (uri);
        videoView.setOnPreparedListener (new MediaPlayer.OnPreparedListener () {
            @Override
            public void onPrepared (MediaPlayer mp) {
                mp.start ();
            }
        });
        new Handler ().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(splash_screen.this, login.class);
                startActivity(i);
                finish(); } }, 3000);
    }
}
