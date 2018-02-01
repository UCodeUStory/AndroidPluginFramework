package com.wangpos.plugindevelopment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wangpos.plugindevelopment.plugin.FileUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;



/**
 * 1.先build插件工程
 * 2.在build编写插件脚本，注意文件路径要修改，否者后面各种报错，坑到你报错NotFound Class.dex
 * 3.执行makeJar
 */
public class LoadDexSampleActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);
        final TextView text = (TextView)findViewById(R.id.text);
        final TextView pluginName = (TextView)findViewById(R.id.pluginName);

        DexClassLoader dexClassLoader = FileUtils.loadDexClass(this,"dynamic_dex3.jar");




//        Resources resources =  FileUtils.getPluginResource(this,"app-debug.apk");


//        String str = resources.getString(resources.getIdentifier("app_name", "string", "com.wangpos.pluginapkdemo"));
//        pluginName.setText("插件名字"+str);

//// 加载图片
//        ImageView iv = (ImageView) findViewById(R.id.testIV);
//        iv.setImageDrawable(resources.getDrawable(resources.getIdentifier("ic_launcher", "mipmap", "h3c.plugina")));
//
//// 加载View
//        XmlResourceParser layoutParser = mBundleResources.getLayout(mBundleResources.getIdentifier("activity_main", "layout", "h3c.plugina"));
//        View bundleView  = LayoutInflater.from(this).inflate(layoutParser, null);
//
//// findView
//        TextView tv = (TextView) findViewById(mBundleResources.getIdentifier("pluginATV", "id", "h3c.plugina"));

//        DexClassLoader dexClassLoader = FileUtils.loadDexClass(this,"app-debug.apk");

        //下面开始加载dex class



//加载的类名为jar文件里面完整类名，写错会找不到此类hh

        Class libClazz = null;
        try {
            libClazz = dexClassLoader.loadClass("com.wangpos.pluginapkdemo.Utils");
            Object dynamic = libClazz.newInstance();

            if (dynamic!=null){
                Method method = libClazz.getDeclaredMethod("getPluginVersion");

                String textStr = (String)method.invoke(dynamic);
                Toast.makeText(this,"插件加载成功"+textStr,Toast.LENGTH_SHORT).show();
                text.setText("dex插件加载成功=");
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

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoadApkSampleActivity.class));
            }
        });


    }



}
