package com.myself.network;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.myself.network.SSL.SSLConfig;
import com.myself.network.SSL.SSLTrustWhich;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    TextView show_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show_board = findViewById(R.id.tvId);
        myRequetPermission();//给权限
    }

    private void myRequetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED) {
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }

    public void get_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                String response = NetworkUtil.getInstance().doGet("http://jobs8.cn:8090/get?name=dio&age=100");
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_func(View view) {
        new Thread(new Runnable() {//开启线程
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String urlStr = "http://jobs8.cn:8090/post";
                JSONObject param_json = new JSONObject();
                try {
                    param_json.put("name", "JOJO");
                    param_json.put("age", 30);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestData = param_json.toString();
                String response = NetworkUtil.getInstance().doPost(urlStr, requestData);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post2_func(View view) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String urlStr = "http://jobs8.cn:8090/post?name=JOJO&age=18";
                JSONObject param_json = new JSONObject();
                try {
                    param_json.put("name", "JOJO");
                    param_json.put("age", 30);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestData = param_json.toString();
                String response = NetworkUtil.getInstance().doPost(urlStr, requestData);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    //上传图片
    public void uploadImage_func(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            myRequetPermission();
            return;
        }
        final String urlStr = "http://jobs8.cn:8090/upload";
        File dataDir = Environment.getExternalStorageDirectory();
        String path = dataDir.getPath() + File.separator + "Pictures" + File.separator;
        final String imgPath = path + "test.png";
//        final String imgPath = new ImageUtil().getPathByImage("money.jpg");
//        Log.e(TAG, "imgPath:\n" + imgPath);
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                String response = NetworkUtil.getInstance().uploadImage(urlStr, imgPath);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    //上传二进制文件
    public void upload_binary(View view) {
        final String urlStr = "https://upload.gmugmu.com/api/v1/base/resource/image/upload?source_info=eyJhcHBpZCI6IjI2MDAwMyIsInVpZCI6IjMyNDk3MjMyIiwicGFnZSI6ImNvbS5lb21jaGF0Lm1vZHVsZS5ob21lLkhvbWVQYWdlQWN0aXZpdHkiLCJ0aW1lIjoiMTYwNjI0Mjc3MDA5NiJ9&cc=TG73257&dev_name=nubia&oaid=&cpu=[Adreno_(TM)_630][ARMv7_639_placeholder]&lc=37427d9d8660d3f7&osversion=android_22&sid=02Yok7jQeFpBTR+Uz1tDaONajE3oi6XdrdqPwUyM/joBe9tJRE6grYwCweYmrwWh&ndid=&conn=wifi&ram=3650129920&msid=363635333236303436313030303634&icc=89860081133720371180&statistics=9ad290c3317d39cc8be58b98f74c86e7&mtid=e6352e4b164246b8b0be20f9c36f5abe&atid=302e30&tourist=&ongd=302e30&mtxid=00812dc6db02&evid=3535303335643264616635632d343464382d626465332d373262342d3034366638353761&cv=GM4.6.30_Android&proto=&logid=2006201,2001902,2002202,201802,202101,204005,204401,2005402,2006102,2001701,2003301,2005601,2005502,2001501,2005202,204301,2001802,2005801,2003502,2004701,2000802,201007,2001602,2004901,2002101,2003802,2005902,204101,2006001,2004801,2001202&ua=nubiaNX629J&uid=32497232&vv=202010141815&meid=363834373537373230363631353638&smid=&aid=b35517406764bf51";
        File dataDir = Environment.getExternalStorageDirectory();
        String path = dataDir.getPath() + File.separator + "Pictures" + File.separator;
        final String imgPath = path + "test.png";
//        Log.e(TAG, "imgPath:\n" + imgPath);
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                String response = NetworkUtil.getInstance().uploadBinary(urlStr, imgPath);
                System.out.println("response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void get_tls_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                SSLConfig.set(SSLTrustWhich.TrustAll);

                String urlStr = "https://jobs8.cn:8091/get";
                String response = NetworkUtil.getInstance().doGet(urlStr);
                System.out.println("get_tls_func response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_tls_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                SSLConfig.set(SSLTrustWhich.TrustAll);

                String urlStr = "https://jobs8.cn:8091/post";
                JSONObject param_json = new JSONObject();
                try {
                    param_json.put("name", "JOJO");
                    param_json.put("age", 18);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestData = param_json.toString();
                String response = NetworkUtil.getInstance().doPost(urlStr, requestData);
                System.out.println("post_tls_func response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void get_sslping_tls_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                SSLConfig.set(SSLTrustWhich.TrustMeOneway);

                String urlStr = "https://jobs8.cn:8091/get";
                String response = NetworkUtil.getInstance().doGet(urlStr);
                System.out.println("get_sslping_tls_func response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_sslping_tls_func(View view) {
        new Thread(new Runnable() {//开启线程
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                SSLConfig.set(SSLTrustWhich.TrustMeOneway);

                String urlStr = "https://jobs8.cn:8091/post";
                JSONObject param_json = new JSONObject();
                try {
                    param_json.put("name", "JOJO");
                    param_json.put("age", 18);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestData = param_json.toString();
                String response = NetworkUtil.getInstance().doPost(urlStr, requestData);
                System.out.println("post_sslping_tls_func response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void get_tls_twoway_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                SSLConfig.set(SSLTrustWhich.TrustMeTwoway);

                String urlStr = "https://jobs8.cn:8092/get";
                String response = NetworkUtil.getInstance().doGet(urlStr);
                System.out.println("get_tls_twoway_func response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    public void post_tls_twoway_func(View view) {
        new Thread(new Runnable() {//只能在子线程中请求
            @Override
            public void run() {
                SSLConfig.set(SSLTrustWhich.TrustMeTwoway);

                String urlStr = "https://jobs8.cn:8092/post";
                JSONObject param_json = new JSONObject();
                try {
                    param_json.put("name", "Leo");
                    param_json.put("age", 30);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestData = param_json.toString();
                String response = NetworkUtil.getInstance().doPost(urlStr, requestData);
                System.out.println("post_tls_twoway_func response:\n" + response);
                showResponse(response);
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show_board.setText(response);//设置TextView的内容
            }
        });
    }

}
