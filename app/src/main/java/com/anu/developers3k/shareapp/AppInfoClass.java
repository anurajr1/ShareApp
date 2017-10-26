package com.anu.developers3k.shareapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView appname = (TextView)findViewById(R.id.textView);
        appname.setText(label);

        //Drawable appicon = appsManager.getAppIconByPackageName(packageName);

        ImageView appnameicon = (ImageView)findViewById(R.id.imageView);
        appnameicon.setImageDrawable(appsManager.getAppIconByPackageName(packageName));


        appsManager.getApplicationInstllationTime(packageName);
        appsManager.installTimeFromPackageManager(packageName);












        try {
            final PackageManager pm = getPackageManager();
            ApplicationInfo app = this.getPackageManager().getApplicationInfo(packageName, 0);

          //  Drawable icon = PackageManager.getApplicationIcon(app);
          //  String name = PackageManager.getApplicationLabel(app);
          //  return icon;
        } catch (PackageManager.NameNotFoundException e) {
            Toast toast = Toast.makeText(this, "error in getting icon", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
    }
}
