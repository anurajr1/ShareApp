package com.anu.developers3k.shareapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.anu.developers3k.shareapp.AppInfoClass;
import com.anu.developers3k.shareapp.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;
public class InstalledAppAdapter extends RecyclerView.Adapter<InstalledAppAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDataSet;

    int clickNumber = 0;
    private InterstitialAd mInterstitialAd;

    public InstalledAppAdapter(Context context, List<String> list){
        mContext = context;
        mDataSet = list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
            public CardView mCardView;
            public TextView mTextViewLabel;
            public TextView mTextViewPackage;
            public ImageView mImageViewIcon;
            public ImageView mImageOpenInApp;



            public ViewHolder (View v){
                super(v);
                // Get the widgets reference from custom layout
                mCardView = (CardView) v.findViewById(R.id.card_view);
                mTextViewLabel = (TextView) v.findViewById(R.id.app_label);
                mTextViewPackage = (TextView) v.findViewById(R.id.app_package);
                mImageViewIcon = (ImageView) v.findViewById(R.id.iv_icon);
                mImageOpenInApp =(ImageView) v.findViewById(R.id.openinapp);

            }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Initialize a new instance of AppManager class
        AppsManager appsManager = new AppsManager(mContext);

        // Get the current package name
        final String packageName = (String) mDataSet.get(position);

        // Get the current app icon
        Drawable icon = appsManager.getAppIconByPackageName(packageName);

        // Get the current app label
        String label = appsManager.getApplicationLabelByPackageName(packageName);

        // Set the current app label
        holder.mTextViewLabel.setText(label);

        // Set the current app package name
        holder.mTextViewPackage.setText(packageName);

        // Set the current app icon
        holder.mImageViewIcon.setImageDrawable(icon);

        //color of open in app changes according to the theme
      //  holder.mImageOpenInApp.setColorFilter(ContextCompat.getColor(mContext,R.color.colorTheme));
        

        // Set a click listener on the open in app image view
        holder.mCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(clickNumber<=1){
                    clickNumber++;
                }else if(clickNumber>=3) {
                    System.out.print("No Ads will shown");
                }else{
                    clickNumber=3;
                    // show add here

                    // set the ad unit ID
                    mInterstitialAd = new InterstitialAd(mContext);
                    mInterstitialAd.setAdUnitId("");

                    AdRequest adRequestinter = new AdRequest.Builder()
                            .addTestDevice("")
                            .build();
                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequestinter);
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            mInterstitialAd.show();
                        }
                    });
                }
                //System.out.print(packageName);
                Intent i = new Intent(mContext.getApplicationContext(), AppInfoClass.class);
                i.putExtra("packagename", packageName);
                mContext.startActivity(i);

            }
        });

       // holder.bind(list.get(position), listener);


    }

    @Override
    public int getItemCount(){
        // Count the installed apps
       return mDataSet.size();

    }
}
