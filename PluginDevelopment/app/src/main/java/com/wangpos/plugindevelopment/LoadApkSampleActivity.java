package com.wangpos.plugindevelopment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wangpos.plugindevelopment.plugin.FileUtils;
import com.wangpos.plugindevelopment.plugin.Plugin;
import com.wangpos.plugindevelopment.plugin.PluginManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class LoadApkSampleActivity extends AppCompatActivity {

    TextView tvName;
    Button startPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("qiyue","savedInstanceState"+savedInstanceState);

        setContentView(R.layout.activity_load_apk);
        tvName = (TextView)findViewById(R.id.tvText);
        startPlugin = (Button) findViewById(R.id.startPlugin);



        //notice 这部分需要按照不同插件做不同配置
        PluginManager pluginManager = PluginManager.getInstance(this);
        Plugin plugin = new Plugin("app-debug.apk", "2", "", "learn");
        pluginManager.loadApk(plugin);



        startPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPluginActivity("learn","com.wangpos.pluginapkdemo.WelcomeActivity");
            }
        });
    }


    public void startPluginActivity(String pluginName,String ClassName) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("Class", ClassName);
        intent.putExtra("pluginName",pluginName);
        this.startActivity(intent);
    }
}
