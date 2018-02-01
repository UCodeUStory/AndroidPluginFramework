package com.wangpos.plugindevelopment;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import dalvik.system.DexClassLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by qiyue on 2018/2/1.
 */

public class Loader {

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public void setDexClassLoader(DexClassLoader dexClassLoader) {
        this.dexClassLoader = dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    private DexClassLoader dexClassLoader;

    private Resources resources;

    public void loadApk(Context context, String fileName) {

        File cacheFile = FileUtils.getCacheDir(context.getApplicationContext());

        String internalPath = cacheFile.getAbsolutePath() + File.separator + fileName;

        Log.i("qiyue","internalPath="+internalPath);
        File desFile = new File(internalPath);

        try {

            if (!desFile.exists()) {

                desFile.createNewFile();
                FileUtils.copyFiles(context, fileName, desFile);
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        File dexOpt = context.getDir("dexOpt", MODE_PRIVATE);

        dexClassLoader = new DexClassLoader(
                internalPath,
                dexOpt.getAbsolutePath(),
                null,
                context.getClassLoader());

        resources = loadPluginResource(context,internalPath);

    }


    public static Resources loadPluginResource(Context context, String apkPath){
        AssetManager assetManager = createAssetManager(apkPath);
        return new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
    }

    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                    assetManager, apkPath);
            return assetManager;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
