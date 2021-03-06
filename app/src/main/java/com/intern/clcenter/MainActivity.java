package com.intern.clcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.skydoves.medal.MedalAnimation;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    imageView = (ImageView)findViewById(R.id.logo);
      //  imageView.startAnimation(medalAnimation);

        new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);

            startActivity(intent);
            finish();
        }
    }, 2000);


}
}
