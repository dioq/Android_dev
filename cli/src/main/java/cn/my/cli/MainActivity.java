package cn.my.cli;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    final static private String TAG = "dlog";
    private TextView tv;

    private Context context = null;

    ClipboardManager clipboard = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textViewId);

        context = MainActivity.this;

        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void test(View view) {
        String cmd = "/system/bin/cat /proc/meminfo";
        String result = CommandUtil.execCommand(cmd);
        tv.setText(result);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void test2(View view) {
        String[] cmd = {"/system/bin/cat", "/proc/meminfo"};
        String result = execCommand2(cmd);
        tv.setText(result);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void test3(View view) {
        String cmd = "/system/bin/ls -l /proc";
        String result = CommandUtil.execCommand(cmd);
        tv.setText(result);
    }

    public static String execCommand2(String[] cli) {
        StringBuilder result = new StringBuilder();
        try {
            ProcessBuilder cmd = new ProcessBuilder(cli);
            Process process = cmd.start();
            InputStream in = process.getInputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                result.append(new String(buffer, 0, bytesRead));
            }

            in.close();
            return result.toString().trim();
        } catch (IOException e) {
            Log.d(TAG, "IOExceptio:" + e.getMessage());
        }
        return null;
    }
}