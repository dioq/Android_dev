package cn.my.selectfile2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FileCopyActivity";

    private ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.getInstance().requestPermissions(MainActivity.this);

        // 注册 filePickerLauncher，处理文件选择结果
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == MainActivity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        String fileContent = readTextFromUri(uri);
                        Log.d(TAG, fileContent);
                    } else {
                        Log.d(TAG, "File selection canceled");
                    }
                });
    }

    public void select(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // 用于打开系统文件选择器
        intent.addCategory(Intent.CATEGORY_OPENABLE); // 确保选择的文件是可打开的
        intent.setType("*/*"); // 允许用户选择任意类型文件 (*/*)
        filePickerLauncher.launch(intent); // 启动文件选择器
    }

    // Read text content from Uri (example for text files)
    private String readTextFromUri(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = this.getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException:" + e.getMessage());
        }
        return stringBuilder.toString();
    }
}