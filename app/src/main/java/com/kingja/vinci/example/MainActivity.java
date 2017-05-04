package com.kingja.vinci.example;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kingja.vinci.Vinci;

import java.io.IOException;

import static java.lang.System.load;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView iv = (ImageView) findViewById(R.id.iv);

        for (int i = 0; i < 20; i++) {
            Vinci.with(MainActivity.this)
                    .load("https://ss0.bdstatic" +
                            ".com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1493874398&di" +
                            "=f9264cc84c23775c6b1181d542f11681&src=http://scimg" +
                            ".jb51.net/allimg/150819/14-150QZ9194K27.jpg")
                    .placeholderRes(R.mipmap.ic_launcher)
                    .into(iv);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Vinci.with(MainActivity.this)
                        .load("https://ss0.bdstatic" +
                                ".com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1493874398&di" +
                                "=f9264cc84c23775c6b1181d542f11681&src=http://scimg" +
                                ".jb51.net/allimg/150819/14-150QZ9194K27.jpg")
                        .placeholderRes(R.mipmap.ic_launcher)
                        .into(iv);
            }
        },5000);


    }
}
