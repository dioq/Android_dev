package cn.my.resource_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "resource_manager";
    private TextView tv;

    private Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tvId);
        context = MainActivity.this;
    }

    public void load1(View view) {
        // 使用 Resources 获取 res 目录下的资源
        String appName = getResources().getString(R.string.app_name);
        Log.d(TAG, "appName:" + appName);
    }

    public void load2(View view) {
        // 使用 AssetManager 获取 assets 目录下的资源
        try {
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            InputStream inputStream = assetManager.open("config.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

            //转化为json对象
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            System.out.println(jsonObject);
        } catch (IOException | JSONException e) {
            Log.d(TAG, "IOException:" + e.getMessage());
        }
    }

    public void load3(View view) {
        try {
            // 获取AssetManager
            AssetManager assetManager = context.getAssets();
            // 列出assets目录下所有文件
            String[] filePathList = assetManager.list("");
            assert filePathList != null;
            Log.d(TAG, "----------------- assets目录下所有文件 -----------------");
            for (String filePath : filePathList) {
                Log.d(TAG, filePath);
            }
        } catch (IOException e) {
            Log.d(TAG, "IOException:" + e.getMessage());
        }
    }
}