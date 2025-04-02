package cn.my.parseapk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "dlog";

    private ImageView imageView;

    private Context mContext = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imgV1);
        mContext = this;
    }

    public void parse(View view) {
        String apk_name = "com.liuzh.deviceinfo_v2.9.5.apk";
        String storeDir = "/data/local/tmp";
        File apkFile = new File(storeDir, apk_name);
        if (apkFile.exists()) {
            try {
                parseApk(this, apkFile);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException:" + e.getMessage());
            }
        }
    }

    @SuppressLint("DefaultLocale")
    public void parseApk(Context context, File apkFile) throws JSONException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkFile.getPath(), PackageManager.GET_ACTIVITIES);
        assert packageInfo != null;

        // activity信息
        ActivityInfo[] activities = packageInfo.activities;
        for (int i = 0; i < Objects.requireNonNull(activities).length; i++) {
            ActivityInfo activityInfo = activities[i];
            String msg = String.format("activities[%d] name:%s,theme:%s,launchMode:%S,taskAffinity:%s",
                    i, activityInfo.name, activityInfo.theme, activityInfo.launchMode, activityInfo.taskAffinity);
            Log.d(TAG, msg);
        }

        JSONObject jsonObject = new JSONObject();
        // 包名
        String packageName = packageInfo.packageName;
        jsonObject.put("packageName", packageName);
        // 版本号
        String versionName = packageInfo.versionName;
        jsonObject.put("versionName", versionName);

        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        // 必须加这两句，不然下面icon获取是default icon而不是应用包的icon
        assert applicationInfo != null;
        applicationInfo.sourceDir = apkFile.getPath();
        applicationInfo.publicSourceDir = apkFile.getPath();
        //应用图标
        Drawable appIcon = packageManager.getApplicationIcon(applicationInfo);
        imageView.setImageDrawable(appIcon);

        //应用名
        String appName = applicationInfo.loadLabel(packageManager).toString();
        jsonObject.put("appName", appName);

        Log.d(TAG, jsonObject.toString());
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void show_all(View view) {
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> mPacks = packageManager.getInstalledPackages(0);
        for (int i = 0; i < mPacks.size(); i++) {
            Log.d(TAG, i + "===============================");
            JSONObject jsonObject = new JSONObject();
            PackageInfo packageInfo = mPacks.get(i);
            //是否是系统应用
            assert packageInfo.applicationInfo != null;
            boolean isUserApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;

            try {
                jsonObject.put("isUserApp", isUserApp);
                // 包名
                String packageName = packageInfo.packageName;
                jsonObject.put("packageName", packageName);
                // 版本号
                String versionName = packageInfo.versionName;
                jsonObject.put("versionName", versionName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Log.d(TAG, jsonObject.toString());
        }
    }
}