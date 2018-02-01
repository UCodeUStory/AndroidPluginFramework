package com.wangpos.plugindevelopment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class LoadApkActivity extends AppCompatActivity implements IPlugin{


    @Override
    protected void attachBaseContext(Context newBase) {
        Log.i("qiyue","LoadApkActivity>>>>>>>>>"+newBase);
        super.attachBaseContext(newBase);
    }

    TextView tvName;
    Button startPlugin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("qiyue","savedInstanceState"+savedInstanceState);

        setContentView(R.layout.activity_load_apk);
        tvName = (TextView)findViewById(R.id.tvText);
        startPlugin = (Button) findViewById(R.id.startPlugin);

        FileUtils fileUtils = new FileUtils();
//
        DexClassLoader dexClassLoader = fileUtils.loadApk(this,"app-debug.apk");

        Class libClazz = null;
        try {
            libClazz = dexClassLoader.loadClass("com.wangpos.pluginapkdemo.Utils");
            Object dynamic = libClazz.newInstance();

            if (dynamic!=null){
                Method method = libClazz.getDeclaredMethod("getPluginVersion");

                String textStr = (String)method.invoke(dynamic);
                Toast.makeText(this,"apk插件加载成功"+textStr,Toast.LENGTH_SHORT).show();

                String str = fileUtils.resources.getString(fileUtils.resources.getIdentifier("app_name", "string", "com.wangpos.pluginapkdemo"));
                tvName.setText("插件名字"+str);
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

        startPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPluginActivity("com.wangpos.pluginapkdemo.WelcomeActivity");
            }
        });

    }


    public  void loadApk(String apkPath) {


        Log.v("loadDexClasses", "Dex Preparing to loadDexClasses!");

        File dexOpt = this.getDir("dexOpt", MODE_PRIVATE);
        final DexClassLoader classloader = new DexClassLoader(
                apkPath,
                dexOpt.getAbsolutePath(),
                null,
                this.getClassLoader());

        Log.v("loadDexClasses", "Searching for class : "
                + "com.registry.Registry");
        try {
            Class<?> classToLoad = (Class<?>) classloader.loadClass("com.dexclassdemo.liuguangli.apkbeloaded.ClassToBeLoad");
            Object instance = classToLoad.newInstance();
            Method method = classToLoad.getMethod("method");
            method.invoke(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void startPluginActivity(String ClassName) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("Class", ClassName);
        this.startActivity(intent);
    }
}
