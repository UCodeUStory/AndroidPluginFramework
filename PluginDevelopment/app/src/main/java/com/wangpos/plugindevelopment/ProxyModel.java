package com.wangpos.plugindevelopment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.wangpos.inter.IPlugin;
import com.wangpos.plugindevelopment.plugin.IProxy;
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
    //notice 通过iProxy接口可以 将公共的操作放到接口里面，从而适配其他类型Activity，否者Activity总变参数也会跟着变，这是简单技巧
    public void onCreate(IProxy iProxy, Bundle savedInstanceState , String className){
        Class localClass = null;
        try {
            localClass = dexClassLoader.loadClass(className);
            Object instance = localClass.newInstance();

            if (instance != null) {
                Method setProxy = localClass.getMethod("attach", new Class[]{Activity.class});
                setProxy.setAccessible(true);
                setProxy.invoke(instance, new Object[]{iProxy});

                //notice 开始调用插件的onCreate() 建立绑定关系，Proxy和Plugin互相引用
                Log.i("qiyue", "调用插件onCreate" + savedInstanceState);
                Method onCreate = localClass.getDeclaredMethod("onCreate",
                        new Class[]{Bundle.class});

                //notice 1.先绑定插件，因为下面执行完onCreate后插件的其他方法也会被回调
                iProxy.attach((IPlugin)instance);
                onCreate.setAccessible(true);
                //notice 2.插件再绑定代理，
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

                //notice 这部分需要按照不同插件做不同配置
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
