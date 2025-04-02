package cn.my.sandbox_study;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
    }

    private static final class InstanceHolder {
        static final FileUtils instance = new FileUtils();
    }

    public static FileUtils getInstance() {
        return InstanceHolder.instance;
    }

    public byte[] read(File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        int length = fin.available();
        byte[] buff = new byte[length];
        fin.read(buff);
        fin.close();
        return buff;
    }

    public void write(File file, byte[] data) throws IOException {
        // 创建基于文件的输出流
        FileOutputStream fos = new FileOutputStream(file);
        // 把数据写入到输出流
        fos.write(data);
        // 关闭输出流
        fos.close();
    }

    // 复制文件
    public void copyFile(File srcFile, File dstFile) throws IOException {
        if (dstFile.exists()) {
            dstFile.delete();
        }

        FileInputStream fileInputStream = new FileInputStream(srcFile);
        FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
        byte[] data = new byte[0x1000];
        int len = -1;
        while ((len = fileInputStream.read(data)) != -1) {
            fileOutputStream.write(data, 0, len);
            fileOutputStream.flush();
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
