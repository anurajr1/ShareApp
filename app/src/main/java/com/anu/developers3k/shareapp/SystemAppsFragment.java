package com.anu.developers3k.shareapp;

/**
 * Created by Anuraj on 08/20/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.anu.developers3k.shareapp.adapter.SystemAppAdapter;
import com.anu.developers3k.shareapp.adapter.SystemAppsManager;

public class SystemAppsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private RecyclerView.Adapter mAdapter;
    View rootView;
    public SystemAppsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.systemappslayout, container, false);

        // Get the application context
        mContext = rootView.getContext();
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.srl);
        recyclerView =
                (RecyclerView) rootView.findViewById(R.id.system_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new SystemAppAdapter(
                getContext(),
                new SystemAppsManager(getContext()).getInstalledPackages()
        );
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
