//package com.anu.developers3k.shareapp.adapter;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.anu.developers3k.shareapp.R;
//import com.anu.developers3k.shareapp.models.App;
//
//import java.io.File;
//import java.util.List;
//
//public class ApkAdapter extends RecyclerView.Adapter<ApkAdapter.AppViewHolder> {
//    Context context;
//    List<App> apps;
//    List<ApplicationInfo> info;
//    ApkAdapter(Context context, List<App> apps, List<ApplicationInfo> info){
//        this.context = context;
//        this.apps = apps;
//        this.info=info;
//    }
//
//    @Override
//    public AppViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_list_item, viewGroup, false);
//        return new AppViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(AppViewHolder appViewHolder, final int i) {
//        appViewHolder.appName.setText(apps.get(i).getappName());
//        long apkSize = apps.get(i).getApkSize();
//
//        appViewHolder.apkSize.setText(getHumanReadableSize(apkSize));
//        appViewHolder.appIcon.setImageDrawable(apps.get(i).getappIcon());
//        appViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveShareDialog(i);
//            }
//        });
//    }
//
//    private void saveShareDialog(final int i)
//    {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.apk_dialog);
//
//        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //
//
//        //dialog.setTitle("ShareApp");
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        ImageView close = (ImageView) dialog.findViewById(R.id.imgClose);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        ImageView share = (ImageView) dialog.findViewById(R.id.imgShare);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareApk(i);
//                dialog.dismiss();
//            }
//        });
//        ImageView save = (ImageView) dialog.findViewById(R.id.imgSave);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveApk(i);
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
//
//
//    private void saveApk(int i)
//    {
//        final ApplicationInfo infos=info.get(i);
//
//        final SaveApp saveApp = new SaveApp();
//        try {
//            String dst = saveApp.extractWithoutRoot(infos);
//            Toast.makeText(context, "App Saved Successfully", Toast.LENGTH_SHORT).show();
//            return;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        new AlertDialog.Builder(context)
//                .setTitle("ShareApp")
//                .setMessage("Root Permission Required!")
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                            String dst = saveApp.extractWithRoot(infos);
//                            Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(context, "Save Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).setNegativeButton("Cancel", null)
//                .show();
//    }
//
//    private void shareApk(int i)
//    {
//        Intent shareApkIntent = new Intent();
//        shareApkIntent.setAction(Intent.ACTION_SEND);
//        shareApkIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apps.get(i).getApkPath())));
//        shareApkIntent.setType("application/vnd.android.package-archive");
//
//        context.startActivity(Intent.createChooser(shareApkIntent, context.getString(R.string.share_apk_dialog_title)));
//    }
//
//
//    public void filterAppbyName(String appName)
//    {
//
//
//    }
//
//
//    private String getHumanReadableSize(long apkSize) {
//        String humanReadableSize;
//        if (apkSize < 1024) {
//            humanReadableSize = String.format(
//                    context.getString(R.string.app_size_b),
//                    (double) apkSize
//            );
//        } else if (apkSize < Math.pow(1024, 2)) {
//            humanReadableSize = String.format(
//                    context.getString(R.string.app_size_kib),
//                    (double) (apkSize / 1024)
//            );
//        } else if (apkSize < Math.pow(1024, 3)) {
//            humanReadableSize = String.format(
//                    context.getString(R.string.app_size_mib),
//                    (double) (apkSize / Math.pow(1024, 2))
//            );
//        } else {
//            humanReadableSize = String.format(
//                    context.getString(R.string.app_size_gib),
//                    (double) (apkSize / Math.pow(1024, 3))
//            );
//        }
//        return humanReadableSize;
//    }
//
//    @Override
//    public int getItemCount() {
//        return apps.size();
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    public static class AppViewHolder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        TextView appName;
//        TextView apkSize;
//        ImageView appIcon;
//        AppViewHolder(View itemView) {
//            super(itemView);
//            cardView = (CardView) itemView.findViewById(R.id.app_row);
//            appName = (TextView) itemView.findViewById(R.id.app_name);
//            apkSize = (TextView) itemView.findViewById(R.id.apk_size);
//            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
//        }
//    }
//}