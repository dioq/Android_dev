package cn.my.cli;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class CommandUtil {
    private static final String TAG = "dlog";

    public static String execCommand(String command) {
        StringBuilder result = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);
            if (process.waitFor() != 0) {
                Log.d(TAG, "exit value = " + process.exitValue());
                return null;
            }

            InputStream in = process.getInputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                result.append(new String(buffer, 0, bytesRead));
            }

            in.close();
            return result.toString().trim();
        } catch (IOException e) {
            Log.d(TAG, "IOException:" + e.getMessage());
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException:" + e.getMessage());
        }
        return null;
    }
}
