package com.wangpos.plugindevelopment.plugin;

import dalvik.system.DexClassLoader;

/**
 * Created by qiyue on 2018/2/1.
 */

public interface IProxy {
    void setDexClassLoader(DexClassLoader dexClassLoader);
}
