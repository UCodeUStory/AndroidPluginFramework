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

/**
 * 每个插件对应一个代理的ProxyActivity,添加第二个插件需要写一个ProxyActivity2 重写对应的proxyModel即可
 */
public class ProxyActivity extends Activity {
    private ProxyModel proxyModel;

    @Override
    protected void attachBaseContext(Context context) {
        Log.i("qiyue", "proxyActivity=" + context);
        proxyModel = new ProxyModel();
        proxyModel.replaceContextResources(context);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("Class");
        proxyModel.onCreate(this,savedInstanceState,className);
    }






}
