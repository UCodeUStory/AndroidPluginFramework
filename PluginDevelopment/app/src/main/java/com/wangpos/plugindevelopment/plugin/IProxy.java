package com.wangpos.plugindevelopment.plugin;

import com.wangpos.inter.IPlugin;

import dalvik.system.DexClassLoader;

/**
 * Created by qiyue on 2018/2/1.
 */

public interface IProxy {
    public void attach(IPlugin pluginActivity);
}
