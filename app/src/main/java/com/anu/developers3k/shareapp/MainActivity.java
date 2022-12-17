package com.anu.developers3k.shareapp;

import android.os.Bundle;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;

    boolean isActivityIsVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //setup the bottom tab
        setupBottomTab();
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);


        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                bottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        super.onPause();
        isActivityIsVisible = false;
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        isActivityIsVisible = true;
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupBottomTab(){
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.user_text, R.drawable.ic_user, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.system_text, R.drawable.ic_system, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.about, R.drawable.ic_aboutus_white, R.color.colorPrimary);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        //bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //    bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                viewPager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_info) {
//
//            AlertDialog.Builder builder =
//                    new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
//            builder.setTitle("User Info");
//            builder.setMessage(getString(R.string.user_info_string));
//            builder.setPositiveButton("OK", null);
//            builder.show();
//            // Do something
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    //back press in device handled
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else {
            super.onBackPressed();
            return;
        }
    }
}
