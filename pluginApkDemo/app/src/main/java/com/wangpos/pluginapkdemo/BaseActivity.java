package com.wangpos.pluginapkdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity {


    // 通过隐式调用宿主的ProxyActivity
    public static final String PROXY_VIEW_ACTION = "com.wangpos.plugindevelopment";
    // 因为插件的Activity没有Context，所以一切与Context的行为都必须通过宿主代理Activity实现！

    protected Activity mProxyActivity;

    public void setProxy(Activity proxyActivity) {
        mProxyActivity = proxyActivity;

        //setInterface   ,两端需要定义一样的接口，然后进行强制转换【
    }

    @Override
    public void setContentView(int layoutResID) {
        mProxyActivity.setContentView(layoutResID);
    }

    @Override
    public View findViewById(int id) {
        return mProxyActivity.findViewById(id);
    }

    // 插件的startActivity其实就是调用宿主开启另一个ProxyActivity
    public void startActivity(String className) {
        Intent intent = new Intent(PROXY_VIEW_ACTION);
        intent.putExtra("Class", className);
        mProxyActivity.startActivity(intent);
    }



}
