package com.cn.lenny.androidhighlights.utils;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public class ExceptionHandler {
    private static boolean RELEASE = true;
    private static Application instance;
    private static Handler mMainHandler = new Handler(Looper.getMainLooper());

    public ExceptionHandler() {
    }

    public static void init(Application mContext, boolean isRelease) {
        RELEASE = isRelease;
        instance = mContext;
    }

    public static void handleException(Throwable throwable) {
        handleException("error", throwable);
    }

    public static void handleException(String tag, final Throwable throwable) {
        if (!RELEASE) {
            if (null != instance) {
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ExceptionHandler.instance, "程序发生异常，请关注log或日志文件", Toast.LENGTH_SHORT).show();
                        if (null != throwable) {
                            throwable.printStackTrace();
                        }

                    }
                });
            }

            Log.e(tag, "程序出现异常", throwable);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            if (null != throwable) {
                throwable.printStackTrace(pw);
            }

            String error = sw.toString();
            if ("mounted".equals(Environment.getExternalStorageState()) && instance.getExternalFilesDir((String)null) != null) {
                StringBuffer fileNameSb = new StringBuffer();
                fileNameSb.append(instance.getExternalFilesDir((String)null).getAbsolutePath());
                fileNameSb.append(File.separator);
                fileNameSb.append("log");
                fileNameSb.append(File.separator);
                fileNameSb.append("exception-");
                fileNameSb.append((new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Date()));
                writeStringToFile(fileNameSb.toString(), error, false);
            }
        }

    }

    protected static void writeStringToFile(String filePath, String content, boolean isAppend) {
        char[] buffer = null;
        int count = 0;
        BufferedReader br = null;
        BufferedWriter bw = null;
        File file = null;

        try {
            file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                file.createNewFile();
            }

            if (file.exists()) {
                br = new BufferedReader(new StringReader(content));
                bw = new BufferedWriter(new FileWriter(file, isAppend));
                buffer = new char[16384];

                int len;
                for(boolean var8 = false; (len = br.read(buffer, 0, buffer.length)) != -1; count += len) {
                    bw.write(buffer, 0, len);
                }

                bw.flush();
            }
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                    bw = null;
                }

                if (br != null) {
                    br.close();
                    br = null;
                }

                buffer = null;
            } catch (Exception var16) {
                var16.printStackTrace();
            }

        }

    }
}
