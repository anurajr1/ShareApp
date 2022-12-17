package com.anu.developers3k.shareapp.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anu.developers3k.shareapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anuraj R(a4anurajr@gmail.com)
 */
public class DeviceInfoAdapter extends ArrayAdapter {
    private List list= new ArrayList();

    public DeviceInfoAdapter(Context context, int resource) {
        super(context, resource);
        // TODO Auto-generated constructor stub
    }
    public void add(DeviceInfoManager object) {
        // TODO Auto-generated method stub
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.list.get(position);
    }
    static class ImgHolder
    {
        TextView DEVICENAME;
        TextView DEVICEVALUE;
        ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ImgHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.deviceinfolayout,parent,false);
            holder = new ImgHolder();
            holder.DEVICENAME = (TextView) row.findViewById(R.id.deviceinfo);
            holder.DEVICEVALUE = (TextView) row.findViewById(R.id.deviceinfo_value);
            row.setTag(holder);
        }
        else
        {
            holder = (ImgHolder) row.getTag();

        }

        DeviceInfoManager FR = (DeviceInfoManager) getItem(position);
        holder.DEVICENAME.setText(FR.getDeviceinfoname());
        holder.DEVICEVALUE.setText(FR.getDeviceinfovalue());
        if(FR.getDeviceinfovalue().equalsIgnoreCase("Denied")){
            holder.DEVICEVALUE.setTextColor(Color.RED);
        }
        else{
            holder.DEVICEVALUE.setTextColor(ContextCompat.getColor(getContext(), R.color.allowedColor));
        }
        return row;
    }
}
