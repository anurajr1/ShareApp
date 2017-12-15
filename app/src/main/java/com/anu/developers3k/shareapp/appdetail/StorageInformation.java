package com.anu.developers3k.shareapp.appdetail;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

//import android.content.pm.
/**
 * Created by i321994 on 14/12/17.
 */

public class StorageInformation {

    long packageSize = 0;
    AppDetails cAppDetails;
    public ArrayList<AppDetails.PackageInfoStruct> res;
    Context context;

    public StorageInformation(Context context){
        this.context=context;
    }

    public void getpackageSize() {
        cAppDetails = new AppDetails((Activity) context);
        res = cAppDetails.getPackages();
        if (res == null)
            return;
        for (int m = 0; m < res.size(); m++) {
            // Create object to access Package Manager
            PackageManager pm = context.getPackageManager();
            Method getPackageSizeInfo;
            try {
                getPackageSizeInfo = pm.getClass().getMethod(
                        "getPackageSizeInfo", String.class,
                        IPackageStatsObserver.class);
                getPackageSizeInfo.invoke(pm, res.get(m).pname,
                        new cachePackState()); //Call the inner class
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    /*
    * Inner class which will get the data size for application
    * */
    private class cachePackState extends IPackageStatsObserver.Stub {
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            Log.w("Package Name", pStats.packageName + "");
            Log.i("Cache Size", pStats.cacheSize + "");
            Log.v("Data Size", pStats.dataSize + "");
            packageSize = pStats.dataSize + pStats.cacheSize;
            Log.v("Total Cache Size", " " + packageSize);
            Log.v("APK Size",pStats.codeSize+"");
        }
    }
}