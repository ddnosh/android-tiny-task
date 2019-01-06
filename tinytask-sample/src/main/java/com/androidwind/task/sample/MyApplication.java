package com.androidwind.task.sample;

import android.app.Application;
import android.os.Environment;

import com.androidwind.log.BuildConfig;
import com.androidwind.log.TinyLog;

import java.io.File;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends Application {

    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        TinyLog.config().setEnable(BuildConfig.DEBUG).setWritable(true).setLogPath(getLogDir()).setFileSize(1).apply();
        TinyLog.v("this is tinylog");
    }

    private String getLogDir() {
        String logDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logDir = Environment.getExternalStorageDirectory().getPath();
        } else {
            logDir = getFilesDir().getPath();
        }
        return logDir + File.separator + "Log";
    }

}
