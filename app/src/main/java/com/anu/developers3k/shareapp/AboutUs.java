package com.anu.developers3k.shareapp;

/**
 * Created by Anuraj on 09/06/2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutUs extends Fragment {
    View rootView;
    public AboutUs(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.aboutus, container, false);
        TextView rateApp = (TextView) rootView.findViewById(R.id.textView5);
        rateApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.anu.developers3k.shareapp"));
                startActivity(browserIntent);
            }
        });

        TextView moreApp = (TextView) rootView.findViewById(R.id.textView6);
        moreApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6807300839110548631"));
                startActivity(browserIntent);
            }
        });

        TextView shareApp = (TextView) rootView.findViewById(R.id.textView7);
        shareApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Download From Playstore :-) \n"+"https://play.google.com/store/apps/details?id=com.anu.developers3k.shareapp";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Backup and Share APK..");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        return rootView;
    }
}
