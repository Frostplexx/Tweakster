package com.example.tweakster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class OtherFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other, container, false);
        ArrayList<ExampleItem> exampleList =  new ArrayList<>();
        //add all the options
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_battery, "Battery Calibartion", "calibrate your Battery!"));
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_build_24, "Build.prop Editor", "Edit the build.prop file of your phone"));
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_memory_24, "CPU Info", "get Info about your CPU"));
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_battery_unknown_24, "Battery Usage", "Check which apps are using your Battery"));
        //https://github.com/librespeed/speedtest-android
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_speed_24, "Speed Test", "Test your Internet Speed"));

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}

