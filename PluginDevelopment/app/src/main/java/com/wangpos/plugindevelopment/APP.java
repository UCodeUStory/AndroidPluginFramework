package com.wangpos.plugindevelopment;

import android.app.Application;
import android.content.pm.PackageManager;

import dalvik.system.DexClassLoader;

/**
 * Created by qiyue on 2018/1/29.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FileUtils fileUtils = new FileUtils();

        DexClassLoader dexClassLoader = fileUtils.loadApk(this,"app-debug.apk");
    }
}
