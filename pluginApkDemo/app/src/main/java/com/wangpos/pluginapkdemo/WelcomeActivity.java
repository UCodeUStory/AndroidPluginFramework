package com.wangpos.pluginapkdemo;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by qiyue on 2018/1/31.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            super.onCreate(savedInstanceState);
        }

        Log.i("qiyue","插件onCreate被调用"+savedInstanceState);
        setContentView(R.layout.activity_base);
    }


}
