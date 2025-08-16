package com.example.thread1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "dlog";

    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvId);
    }

    public void test1(View view) {
        Thread t = new MyThread();
        t.start();
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "test1 for thread!");
        }
    }

    public void test2(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "test2 for thread!");
            }
        }).start();
    }
}
