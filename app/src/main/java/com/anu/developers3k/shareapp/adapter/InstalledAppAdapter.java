package com.anu.developers3k.shareapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.anu.developers3k.shareapp.R;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.util.List;

import static android.R.id.primary;
import static java.security.AccessController.getContext;

public class InstalledAppAdapter extends RecyclerView.Adapter<InstalledAppAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDataSet;
    private SlideUp slideUp;
    public View sliderView;

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
        holder.mImageOpenInApp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Get the intent to launch the specified application
//                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
//                if (intent != null) {
//                    mContext.startActivity(intent);
//                } else {
//                    Toast.makeText(mContext, packageName + " Launch Error.", Toast.LENGTH_SHORT).show();
//                }

//                SlidrConfig config = new SlidrConfig.Builder()
//                        .primaryColor(getResources().getColor(R.color.primary)
//                                .secondaryColor(getResources().getColor(R.color.secondary)
//                                        .position(SlidrPosition.LEFT)
//                                        .sensitivity(1f)
//                                        .scrimColor(Color.BLACK)
//                                        .scrimStartAlpha(0.8f)
//                                        .scrimEndAlpha(0f)
//                                        .velocityThreshold(2400)
//                                        .distanceThreshold(0.25f)
//                                        .build();
//                LayoutInflater  mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                mInflater.inflate(R.layout.slideview, null, true);

                View view1 = View.inflate(mContext, R.layout.slideview, null);
                sliderView = view1.findViewById(R.id.slideView);
//                slideUp = new SlideUp(slideView);
//                slideUp.
                slideUp = new SlideUpBuilder(sliderView)
                        .withStartState(SlideUp.State.HIDDEN)
                        .withStartGravity(Gravity.BOTTOM)
                        .build();
            }
        });




    }

    @Override
    public int getItemCount(){
        // Count the installed apps
       return mDataSet.size();

    }
}