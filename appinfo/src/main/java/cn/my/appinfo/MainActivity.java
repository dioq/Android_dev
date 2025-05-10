package cn.my.appinfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final static private String TAG = "dlog";
    private TextView tv;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textViewId);
        context = MainActivity.this;
    }

    public void show1(View view) {
        String pkg_name = context.getPackageName();
        Log.d(TAG, "pkg name:" + pkg_name);
        String ps_name = android.os.Process.myProcessName();
        Log.d(TAG, "process_name:" + ps_name);
    }
}