package com.king.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.king.view.counterview.CounterView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CounterView counterView;

    private Random mRandom;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRandom = new Random();

        counterView = (CounterView)findViewById(R.id.counterView);
        counterView.setText("Random");
        counterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterView.showAnimation(random(10000),CounterView.getDecimalFormat(count));

                if(count<4){
                    count++;
                }else{
                    count = 0 ;
                }
            }
        });


    }


    public int random(int n){
        return mRandom.nextInt(n);
    }
}
