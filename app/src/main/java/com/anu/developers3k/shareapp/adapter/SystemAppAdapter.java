package com.anu.developers3k.shareapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anu.developers3k.shareapp.AppInfoClass;
import com.anu.developers3k.shareapp.R;

import java.util.List;

public class SystemAppAdapter extends RecyclerView.Adapter<SystemAppAdapter.ViewHolder> {
    private Context mContext;
    public List<String> mDataSet;

    public SystemAppAdapter(Context context, List<String> list){
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Initialize a new instance of SystemAppsManager class
        SystemAppsManager SystemAppsManager = new SystemAppsManager(mContext);

        // Get the current package name
        final String packageName = (String) mDataSet.get(position);

        // Get the current app icon
        Drawable icon = SystemAppsManager.getAppIconByPackageName(packageName);

        // Get the current app label
        String label = SystemAppsManager.getApplicationLabelByPackageName(packageName);

        // Set the current app label
        holder.mTextViewLabel.setText(label);

        // Set the current app package name
        holder.mTextViewPackage.setText(packageName);

        // Set the current app icon
        holder.mImageViewIcon.setImageDrawable(icon);


        // Set a click listener on the open in app image view
        holder.mCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //System.out.print(packageName);
                Intent i = new Intent(mContext.getApplicationContext(), AppInfoClass.class);
                i.putExtra("packagename", packageName);
                mContext.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount(){
        // Count the installed apps
       return mDataSet.size();

    }
}