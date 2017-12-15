package com.anu.developers3k.shareapp.appdetail;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.widget.ListView;

/**
 * Created by i321994 on 14/12/17.
 */

public class AppDetails {
    Activity mActivity;
    public ArrayList<PackageInfoStruct> res = new ArrayList<PackageInfoStruct>();
    public ListView list;
    public String app_labels[];

    public AppDetails(Activity mActivity) {
        this.mActivity = mActivity;

    }

    public ArrayList<PackageInfoStruct> getPackages() {
        ArrayList<PackageInfoStruct> apps = getInstalledApps(false);
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            apps.get(i);
        }
        return apps;
    }

    private ArrayList<PackageInfoStruct> getInstalledApps(boolean getSysPackages) {

        List<PackageInfo> packs = mActivity.getPackageManager()
                .getInstalledPackages(0);
        try {
            app_labels = new String[packs.size()];
        } catch (Exception e) {
            Toast.makeText(mActivity.getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PackageInfoStruct newInfo = new PackageInfoStruct();
            newInfo.appname = p.applicationInfo.loadLabel(
                    mActivity.getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.datadir = p.applicationInfo.dataDir;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(mActivity
                    .getPackageManager());
            res.add(newInfo);

            app_labels[i] = newInfo.appname;
        }
        return res;
    }

    class PackageInfoStruct {
        String appname = "";
        String pname = "";
        String versionName = "";
        int versionCode = 0;
        Drawable icon;
        String datadir = "";
    }
}