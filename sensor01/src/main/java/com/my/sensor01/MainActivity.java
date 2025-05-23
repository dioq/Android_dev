package com.my.sensor01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "dlog";

    private SensorManager sensorManager;
    private Vibrator vibrator;
    private Sensor accelerometerSensor;
    private SensorEventListener sensorEventListener;

    private String vector;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tvId);

        get_system_service();
        listener();
    }

    // 获取系统服务,如传感器、震动服务、音频播放
    private void get_system_service() {
        //获取传感器服务管理对象
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        //获取传感器对象
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册传感器监听器
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //震动服务对象
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        int size = sensorManager.getSensorList(-1).size();
        @SuppressLint("DefaultLocale") String msg = String.format("sensor size:%d", size);
        Log.d(TAG, msg);
    }

    // 监听 传感器
    private void listener() {
        //创建监听器对象
        sensorEventListener = new SensorEventListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSensorChanged(SensorEvent event) {
                int size = event.values.length; // 这就是当前 sensor type 的 size
                Log.d(TAG, "sensor type size:" + size);

                int type = event.sensor.getType();
                if (type == Sensor.TYPE_ACCELEROMETER) {
                    float[] values = event.values;
                    float x = values[0];
                    float y = values[1];
                    float z = values[2];

                    @SuppressLint("DefaultLocale") String msg = String.format("x:%f,y:%f,z:%f\n", x, y, z);
                    Log.v(TAG, msg);

                    vector += msg;
                    showResponse(vector);

                    //作为一个标准值   最小低于 10 最大值不超过 20
                    int coll = 16;
                    if (Math.abs(x) > coll | Math.abs(y) > coll | Math.abs(z) > coll) {
                        //200:摇晃了200毫秒之后，开始震动
                        //500：震动持续的时间，震动持续了500毫秒
                        long[] pattern = {200, 2000};
                        vibrator.vibrate(pattern, -1);
                        Toast.makeText(MainActivity.this, "摇一摇拆红包", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    // 在主线程显示视图控件
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(response);//设置TextView的内容
            }
        });
    }

    public void register(View view) {
        if (sensorManager != null) {
            // 注册传感器监听器
            sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e(TAG, "sensorManager is null");
        }
    }

    public void unregister(View view) {
        if (sensorManager != null) {
            // 注销传感器监听器
            sensorManager.unregisterListener(sensorEventListener);
        } else {
            Log.e(TAG, "sensorManager is null");
        }
    }
}
