package com.kingja.vinci.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         GridView gv = (GridView) findViewById(R.id.grid_view);

        gv.setAdapter(new SampleGridViewAdapter(this));
        gv.setOnScrollListener(new SampleScrollListener(this));

    }
}
