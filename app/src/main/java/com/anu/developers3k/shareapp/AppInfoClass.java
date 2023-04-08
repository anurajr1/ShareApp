package com.anu.developers3k.shareapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.anu.developers3k.shareapp.adapter.AppsManager;
import com.anu.developers3k.shareapp.adapter.DeviceInfoAdapter;
import com.anu.developers3k.shareapp.adapter.DeviceInfoManager;
import com.anu.developers3k.shareapp.helper.Helper;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import static android.content.ContentValues.TAG;


/**
 * Created by Anuraj R(a4anurajr@gmail.com) on 12/01/18.
 */

public class AppInfoClass extends AppCompatActivity {
    PackageManager pm = null;
    private static int SPLASH_TIME_OUT = 400;

    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appdetaillayout);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitions_container);

        Intent intent = getIntent();
        final String packageName = intent.getStringExtra("packagename");

        final AppsManager appsManager = new AppsManager(getApplicationContext());
        // Get the current app label
        String label = appsManager.getApplicationLabelByPackageName(packageName);

        //app name printing
        TextView appname = (TextView)findViewById(R.id.textView);
        appname.setText(label);

        //app icon
        ImageView appnameicon = (ImageView)findViewById(R.id.imageView);
        appnameicon.setImageDrawable(appsManager.getAppIconByPackageName(packageName));

        //first install time
        final TextView firstInstallTime = (TextView)findViewById(R.id.text_versionname1);
        firstInstallTime.setText((appsManager.installTimeFromPackageManager(packageName).toString()));

        //update date time
        final TextView updateTime = (TextView)findViewById(R.id.text_versioncode1);
        updateTime.setText((appsManager.lastUpdateTimeFromPackageManager(packageName)).toString());

        String versionName = "";
        int versionCode = 0;
        try {
            pm = getPackageManager();
            versionName = pm.getPackageInfo(packageName, 0).versionName;
            versionCode = pm.getPackageInfo(packageName, 0).versionCode;
        }catch(Exception e){
            System.out.print("inside the error block");
        }
        //version name
        final TextView versionNametextView = (TextView)findViewById(R.id.text_versionname);
        versionNametextView.setText(versionName);

        //version code
        final TextView versionCodeTextView = (TextView)findViewById(R.id.text_versioncode);
        versionCodeTextView.setText(Integer.toString(versionCode));


        //animation initilization
        TransitionManager.beginDelayedTransition(transitionsContainer);

        //layout id
        final LinearLayout layoutDetail = (LinearLayout)findViewById(R.id.linearid);
        layoutDetail.setVisibility(View.GONE);


        //fetching the permission list
        appsManager.fetchDetail(packageName);

        //granted permission list
        ArrayList<String> grantedList=new ArrayList<String>();//Creating arraylist
        //denied permission list
        ArrayList<String> deniedList=new ArrayList<String>();//Creating arraylist
        grantedList = appsManager.fetchDetail(packageName).grantedPermissionList;
        deniedList = appsManager.fetchDetail(packageName).deniedPermissionList;

        //grantedList.
        HashMap<String, String> hmap = new HashMap<String, String>();

        for(int i=0;i<grantedList.size();i++){
            hmap.put(grantedList.get(i),"Allowed");
        }
        for(int i=0;i<deniedList.size();i++){
            hmap.put(deniedList.get(i),"Denied");
        }

        //on click on list view
        ListView lst = (ListView) findViewById(R.id.device_list);
    //    setListViewHeightBasedOnChildren(lst);

        DeviceInfoAdapter adapter = new DeviceInfoAdapter(getApplicationContext(), R.layout.deviceinfolayout);
        lst.setAdapter(adapter);

         /* Display content using Iterator*/
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            DeviceInfoManager obj = new DeviceInfoManager(mentry.getKey().toString(), mentry.getValue().toString());
            adapter.add(obj);
        }
        //no permisison case
        if(hmap.size()==0){
            TextView txtmsg = (TextView)findViewById(R.id.permission_msg);
            txtmsg.setVisibility(View.VISIBLE);
        }

        //setting the listview fully expanded inside the scrollview
        Helper.getListViewSize(lst);

        //running the pop up animation of App details
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                boolean visible=true;
                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());
                TransitionManager.beginDelayedTransition(transitionsContainer, set);
                layoutDetail.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            }
        }, SPLASH_TIME_OUT);


        PieView pieView = (PieView) findViewById(R.id.pieView1);
        PieAngleAnimation animation = new PieAngleAnimation(pieView);
        animation.setDuration(1000); //This is the duration of the animation in millis
        pieView.startAnimation(animation);

        // Change the color fill of the bar representing the current percentage
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.colorPrimary));

        String sizevalue = null;
        try {
            long size = appsManager.getApkSize(packageName);
            sizevalue = getStringSizeLengthFile(size);
        }catch (Exception e){
        }
        pieView.setInnerText("Size: \n"+sizevalue);



        FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fabMenu.setMenuButtonColorNormalResId(R.color.colorPrimary);
        fabMenu.setMenuButtonColorPressed(R.color.colorPrimaryDark);
        fabMenu.setMenuButtonColorRippleResId(R.color.colorPrimaryDark);

        //fab for apk share
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setColorNormalResId(R.color.colorPrimary);
        fab1.setColorPressedResId(R.color.colorPrimaryDark);

        //fab for apk backup
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setColorNormalResId(R.color.colorPrimary);
        fab2.setColorPressedResId(R.color.colorPrimaryDark);

        //fab more apps in google play
        FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab4.setColorNormalResId(R.color.colorPrimary);
        fab4.setColorPressedResId(R.color.colorPrimaryDark);

        //click listner for all the fab items
        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //checking run time permissions for android M and higher versions
                if(isStoragePermissionGranted()){
                    //sharing the apk file
                    try {
                        appsManager.shareApk(packageName);
                    }catch (Exception e){
                        System.out.print("apk Share failed!!");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.permissionError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted()){
                    //backup the apk file
                    try {
                        appsManager.saveApk(packageName);
                    }catch (Exception e){
                        System.out.print("apk Extraction failed!!");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.permissionError, Toast.LENGTH_SHORT).show();
                }
            }
        });

        fab4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6807300839110548631"));
//                startActivity(browserIntent);
                //open the PlayStore page of the given app
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        });
    }

    //get human readable value for the apk file
    public static String getStringSizeLengthFile(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        float sizeKb = 1024.0f;
        float sizeMo = sizeKb * sizeKb;
        float sizeGo = sizeMo * sizeKb;
        float sizeTerra = sizeGo * sizeKb;
        if(size < sizeMo)
            return df.format(size / sizeKb)+ " KiB";
        else if(size < sizeGo)
            return df.format(size / sizeMo) + " MiB";
        else if(size < sizeTerra)
            return df.format(size / sizeGo) + " GiB";
        return "";
    }
    //closing the current screen by clicking the cross button
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }

    //run time permissions for Android M and higher versions
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT <= 30 ) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk>30 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getBaseContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(getBaseContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

}
