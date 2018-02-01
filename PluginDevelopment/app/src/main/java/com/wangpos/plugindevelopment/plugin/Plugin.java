package com.wangpos.plugindevelopment.plugin;

/**
 * Created by qiyue on 2018/2/1.
 */

public class Plugin {
    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Plugin(String fileName, String version, String path, String key) {
        FileName = fileName;
        this.version = version;
        this.path = path;
        this.key = key;
    }

    private String FileName;

    private String version;

    private String path;

    private String key;
}
