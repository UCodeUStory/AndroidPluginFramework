package com.wangpos.plugindevelopment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class ProxyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("Class");

        FileUtils fileUtils = new FileUtils();

        DexClassLoader dexClassLoader = fileUtils.loadApk(this,"app-debug.apk");

        Class localClass = null;
        try {
            localClass = dexClassLoader.loadClass(className);
            Object instance = localClass.newInstance();

            if (instance!=null){
                Method setProxy = localClass.getMethod("setProxy",new Class[] { Activity.class });
                setProxy.setAccessible(true);
                setProxy.invoke(instance, new Object[] { this });

                // 调用插件的onCreate()
                Log.i("qiyue","调用插件onCreate"+savedInstanceState);
                Method onCreate = localClass.getDeclaredMethod("onCreate",
                        new Class[] { Bundle.class });
                onCreate.setAccessible(true);
                onCreate.invoke(instance, new Object[] { null });

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


    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 反射调用
         */
    }

    // 因为插件Activity获得的是宿主的Context，这样就拿不到自己的资源，所以这里要用插件的Resource替换ProxyActivity的Resource！
    private Resources mBundleResources;

    @Override
    protected void attachBaseContext(Context context) {

        Log.i("qiyue","proxyActivity="+context);
        replaceContextResources(context);
        super.attachBaseContext(context);
    }

    public void replaceContextResources(Context context){
        try {
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            if (null == mBundleResources) {

                FileUtils fileUtils = new FileUtils();

                DexClassLoader dexClassLoader = fileUtils.loadApk(context,"app-debug.apk");

                mBundleResources = fileUtils.resources;
            }
            field.set(context, mBundleResources);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
