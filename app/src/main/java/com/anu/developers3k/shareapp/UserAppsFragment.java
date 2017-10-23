package com.anu.developers3k.shareapp;

/**
 * Created by Anuraj on 08/20/2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.anu.developers3k.shareapp.adapter.AppsManager;
import com.anu.developers3k.shareapp.adapter.InstalledAppAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserAppsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private RecyclerView.Adapter mAdapter;
    View rootView;
    private List<String> mDataSet =  new ArrayList<String>();
    public UserAppsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.userappslayout, container, false);
        // Get the application context
        mContext = rootView.getContext();
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl);
        recyclerView =
                (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        try {
            final HashMap<String, String> map = new HashMap<String, String>();
            if (!new AppsManager(getContext()).getInstalledPackages().isEmpty()) {
                for (String data : new AppsManager(getContext()).getInstalledPackages()) {
                    // Initialize a new instance of AppManager class
                    AppsManager appsManager = new AppsManager(mContext);
                    // Get the current app label
                    String label = appsManager.getApplicationLabelByPackageName(data);
                    map.put(data, label);
                }
            }

            // get entrySet from HashMap object
            Set<Map.Entry<String, String>> AppSet = map.entrySet();

            // convert HashMap to List of Map entries
            List<Map.Entry<String, String>> AppListEntry =
                    new ArrayList<Map.Entry<String, String>>(AppSet);

            // sort list of entries using Collections class utility method sort(ls, cmptr)
            Collections.sort(AppListEntry,
                    new Comparator<Map.Entry<String, String>>() {
                        @Override
                        public int compare(Map.Entry<String, String> es1,
                                           Map.Entry<String, String> es2) {
                            return es1.getValue().compareTo(es2.getValue());
                        }
                    });

            // store into LinkedHashMap for maintaining insertion order
            Map<String, String> AppLHMap =
                    new LinkedHashMap<String, String>();

            // iterating list and storing in LinkedHahsMap
            for (Map.Entry<String, String> map1 : AppListEntry) {
                AppLHMap.put(map1.getKey(), map1.getValue());
            }

            // iterate LinkedHashMap to retrieved stored values
            for (Map.Entry<String, String> lhmap : AppLHMap.entrySet()) {
                System.out.println("Key : " + lhmap.getKey() + "\t\t"
                        + "Value : " + lhmap.getValue());
                mDataSet.add(lhmap.getKey());
            }
        }catch(Exception e){
            System.out.print("User apps error found!");
        }


        mAdapter = new InstalledAppAdapter(
                getContext(),
                mDataSet
        );
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
