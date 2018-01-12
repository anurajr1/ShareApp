package com.anu.developers3k.shareapp.adapter;

/**
 * Created by I321994 on 10/20/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import com.anu.developers3k.shareapp.R;
import com.anu.developers3k.shareapp.models.AppDetails;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AppsManager {
    private static Context mContext;
    static List<ApplicationInfo> packages;
    public AppsManager(Context context){
        mContext = context;
    }

    // Get a list of installed app
    public List<String> getInstalledPackages(){
        // Initialize a new Intent which action is main
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        // Set the newly created intent category to launcher
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Set the intent flags
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK|
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        );
        // Generate a list of ResolveInfo object based on intent filter
        List<ResolveInfo> resolveInfoList = mContext.getPackageManager().queryIntentActivities(intent,0);
        // Initialize a new ArrayList for holding non system package names
        List<String> packageNames = new ArrayList<>();
        // Loop through the ResolveInfo list
        for(ResolveInfo resolveInfo : resolveInfoList){
            // Get the ActivityInfo from current ResolveInfo
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            // If this is not a system app package
            if(!isSystemPackage(resolveInfo)){
                // Add the non system package to the list
                packageNames.add(activityInfo.applicationInfo.packageName);
            }
        }
        return packageNames;
    }

    // Custom method to determine an app is system app
    public boolean isSystemPackage(ResolveInfo resolveInfo){
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    // Custom method to get application icon by package name
    public Drawable getAppIconByPackageName(String packageName){
        Drawable icon;
        try{
            icon = mContext.getPackageManager().getApplicationIcon(packageName);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            // Get a default icon
            icon = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher);
        }
        return icon;
    }

    // Custom method to get application label by package name
    public String getApplicationLabelByPackageName(String packageName){
        PackageManager packageManager = mContext.getPackageManager();
        ApplicationInfo applicationInfo;
        String label = "Unknown";
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if(applicationInfo!=null){
                label = (String)packageManager.getApplicationLabel(applicationInfo);
            }

        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return label;
    }

//    public long getApplicationInstllationTime(String packagename){
//
//        PackageManager pm = mContext.getPackageManager();
//        long installed=0;
//        try {
//            ApplicationInfo appInfo = pm.getApplicationInfo(packagename, 0);
//            String appFile = appInfo.sourceDir;
//            installed = new File(appFile).lastModified(); //Epoch Time
//        }catch(Exception e){
//
//        }
//        return installed;
//    }

    public Date installTimeFromPackageManager(
             String packageName) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            Field field = PackageInfo.class.getField("firstInstallTime");

            long timestamp = field.getLong(info);
            return new Date(timestamp);
        } catch (PackageManager.NameNotFoundException e) {
            return null; // package not found
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (SecurityException e) {
        }
        // field wasn't found
        return null;
    }

    public Date lastUpdateTimeFromPackageManager(
            String packageName) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            Field field = PackageInfo.class.getField("lastUpdateTime");

            long timestamp = field.getLong(info);
            return new Date(timestamp);
        } catch (PackageManager.NameNotFoundException e) {
            return null; // package not found
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (SecurityException e) {
        }
        // field wasn't found
        return null;
    }

    //fetching the permission list of app
    public AppDetails fetchDetail(String packageName) {
        PackageManager packageManager = mContext.getPackageManager();
        AppDetails appDetails = new AppDetails();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName,
                    PackageManager.GET_META_DATA | PackageManager.GET_PERMISSIONS);
            appDetails.name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appDetails.icon = packageInfo.applicationInfo.loadIcon(packageManager);
            appDetails.packageName = packageName;
            if (packageInfo.requestedPermissions != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    for (int index = 0; index < packageInfo.requestedPermissions.length; index++) {
                        if ((packageInfo.requestedPermissionsFlags[index]
                                & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                            appDetails.grantedPermissionList.add(packageInfo.requestedPermissions[index]);
                        } else {
                            appDetails.deniedPermissionList.add(packageInfo.requestedPermissions[index]);
                        }
                    }
                } else {
                    appDetails.grantedPermissionList =
                            new ArrayList<>(Arrays.asList(packageInfo.requestedPermissions));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appDetails;
    }
    public static long getApkSize(String packageName)
            throws PackageManager.NameNotFoundException {
        return new File(mContext.getPackageManager().getApplicationInfo(
                packageName, 0).publicSourceDir).length();
    }

//    public long getAppApkSize(String packageName) {
//        PackageManager packageManager = mContext.getPackageManager();
//        packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
//        long apkSize = 0;
//        for (ApplicationInfo packageInfo : packages) {
//            if ((((ApplicationInfo) packageInfo).packageName).equalsIgnoreCase(packageName)) {
//                //name = packageInfo.packageName;
//                //Drawable icon = packageManager.getApplicationIcon(packageInfo);
//               // String apkPath = packageInfo.sourceDir;
//                apkSize = new File(packageInfo.sourceDir).length();
//            }
//        }
//        return apkSize;
//    }

    //sharing the apk file
    public void shareApk(String packageName)
    {
        Intent shareApkIntent = new Intent();
        shareApkIntent.setAction(Intent.ACTION_SEND);
        PackageManager packageManager = mContext.getPackageManager();
        packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String apkPath = null;
        for (ApplicationInfo packageInfo : packages) {
            if ((((ApplicationInfo) packageInfo).packageName).equalsIgnoreCase(packageName)) {
                apkPath = packageInfo.sourceDir;
            }
        }
        shareApkIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
        shareApkIntent.setType("application/vnd.android.package-archive");
        mContext.startActivity(Intent.createChooser(shareApkIntent, mContext.getString(R.string.share_apk_dialog_title)));
    }


    //backup the apk file
    public void saveApk(String packageName)
    {
        PackageManager packageManager = mContext.getPackageManager();
        packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String apkPath = null;
        for (ApplicationInfo packageInfo : packages) {
            if ((((ApplicationInfo) packageInfo).packageName).equalsIgnoreCase(packageName)) {
                //apkPath = packageInfo.sourceDir;
                final ApplicationInfo infos= packageInfo;
                final SaveApp saveApp = new SaveApp();
                try {
                    String dst = saveApp.extractWithoutRoot(infos);
                    Toast.makeText(mContext, "App Saved Successfully", Toast.LENGTH_SHORT).show();
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new AlertDialog.Builder(mContext)
                        .setTitle("ShareApp")
                        .setMessage("Root Permission Required!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String dst = saveApp.extractWithRoot(infos);
                                    Toast.makeText(mContext, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext, "Save Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("Cancel", null)
                        .show();
            }
        }
    }
}