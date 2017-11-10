package com.anu.developers3k.shareapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.anu.developers3k.shareapp.adapter.AppsManager;

/**
 * Created by i321994 on 25/10/17.
 */

public class AppInfoClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appdetaillayout);

        Intent intent = getIntent();
        String packageName = intent.getStringExtra("packagename");

        AppsManager appsManager = new AppsManager(getApplicationContext());
        // Get the current app label
        String label = appsManager.getApplicationLabelByPackageName(packageName);

        //app name printing
        TextView appname = (TextView)findViewById(R.id.textView);
        appname.setText(label);

        //app icon
        ImageView appnameicon = (ImageView)findViewById(R.id.imageView);
        appnameicon.setImageDrawable(appsManager.getAppIconByPackageName(packageName));


        //first install time
        TextView firstInstallTime = (TextView)findViewById(R.id.text_versionname1);
        firstInstallTime.setText((appsManager.installTimeFromPackageManager(packageName).toString()));

        //update date time
        TextView updateTime = (TextView)findViewById(R.id.text_versioncode1);
        updateTime.setText((appsManager.lastUpdateTimeFromPackageManager(packageName)).toString());

        String versionName = "";
        int versionCode = 0;
        try {
            final PackageManager pm = getPackageManager();
            versionName = pm.getPackageInfo(packageName, 0).versionName;
            versionCode = pm.getPackageInfo(packageName, 0).versionCode;
        }catch(Exception e){
            System.out.print("inside the error block");
        }
        //version name
        TextView versionNametextView = (TextView)findViewById(R.id.text_versionname);
        versionNametextView.setText(versionName);

        //version code
        TextView versionCodeTextView = (TextView)findViewById(R.id.text_versioncode);
        versionCodeTextView.setText(Integer.toString(versionCode));


    }
}
