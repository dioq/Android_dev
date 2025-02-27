package com.myself.logstudy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show1(View view) {
        Toast.makeText(this, "打印日志", Toast.LENGTH_SHORT).show();

        //日志有5个级别，细化了输出信息分类
        /*
         * verbose:描述
         * debug:调试
         * info:普通系统信息
         * warning:警告
         * error:错误
         **/
        Log.v("xxxx", "======== 描述 =========");
        Log.d("xxxx", "======== 调试 =========");
        Log.i("xxxx", "======== 普通系统信息 =========");
        Log.w("xxxx", "======== 警告 =========");
        Log.e("xxxx", "======== 错误 =========");
    }
}
