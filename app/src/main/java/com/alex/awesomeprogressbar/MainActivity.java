package com.alex.awesomeprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alex.awesomeprogressbarlib.AwesomeProgressBar;

public class MainActivity extends AppCompatActivity {
    private AwesomeProgressBar mAwesomeBar;
    private TextView btn_anim, btn_noanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        mAwesomeBar = (AwesomeProgressBar) findViewById(R.id.awesomeBar);
        btn_anim = (TextView) findViewById(R.id.btn_anim);
        btn_noanim = (TextView) findViewById(R.id.btn_noanim);

        btn_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeBar.setRateWithAnim(70, 30);
            }
        });
        btn_noanim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAwesomeBar.setRate(10, 90);
            }
        });

        mAwesomeBar.setRate(10, 90);
    }
}
