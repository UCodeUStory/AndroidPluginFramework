package com.wangpos.pluginapkdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

/**
 * Created by qiyue on 2018/1/31.
 */

public class WelcomeActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("qiyue","插件onCreate被调用"+savedInstanceState);

        that.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);


        ListView listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(new ListAdapter(that));

    }


}
