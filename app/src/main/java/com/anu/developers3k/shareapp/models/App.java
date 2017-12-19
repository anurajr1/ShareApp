package com.anu.developers3k.shareapp.models;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public final class App {
    private String appName;
    private Drawable appIcon;
    private String apkPath;
    private long apkSize;
    ApplicationInfo applicationInfo;

    public App(String appName, Drawable appIcon, String apkPath, long apkSize) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.apkPath = apkPath;
        this.apkSize = apkSize;
    }

    public App(String appName, Drawable appIcon, String apkPath, long apkSize, ApplicationInfo applicationInfo) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.apkPath = apkPath;
        this.apkSize = apkSize;
        this.applicationInfo = applicationInfo;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public String getappName() {
        return appName;
    }

    public Drawable getappIcon() {
        return appIcon;
    }

    public String getApkPath() {
        return apkPath;
    }

    public long getApkSize() {
        return apkSize;
    }
}