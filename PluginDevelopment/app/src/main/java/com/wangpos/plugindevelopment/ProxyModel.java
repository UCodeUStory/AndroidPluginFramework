package com.wangpos.plugindevelopment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.wangpos.plugindevelopment.plugin.Plugin;
import com.wangpos.plugindevelopment.plugin.PluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by qiyue on 2018/2/1.
 */

public class ProxyModel {

    private DexClassLoader dexClassLoader;

    public void onCreate(Activity activity,Bundle savedInstanceState ,String className){
        Class localClass = null;
        try {
            localClass = dexClassLoader.loadClass(className);
            Object instance = localClass.newInstance();

            if (instance != null) {
                Method setProxy = localClass.getMethod("setProxy", new Class[]{Activity.class});
                setProxy.setAccessible(true);
                setProxy.invoke(instance, new Object[]{activity});

                // 调用插件的onCreate()
                Log.i("qiyue", "调用插件onCreate" + savedInstanceState);
                Method onCreate = localClass.getDeclaredMethod("onCreate",
                        new Class[]{Bundle.class});
                onCreate.setAccessible(true);
                onCreate.invoke(instance, new Object[]{null});

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Resources replaceContextResources(Context context) {

        Resources mBundleResources = null;
        try {
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            if (null == mBundleResources) {

                PluginManager pluginManager = PluginManager.getInstance(context);
                Plugin plugin = new Plugin("app-debug.apk", "2", "", "learn");
                pluginManager.loadApk(plugin);
                mBundleResources = pluginManager.getPluginResources("learn");
                dexClassLoader = pluginManager.getClassLoader("learn");

            }
            field.set(context, mBundleResources);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mBundleResources;
    }

}
