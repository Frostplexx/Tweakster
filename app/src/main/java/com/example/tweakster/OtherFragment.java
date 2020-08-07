package com.example.tweakster;

import android.os.Bundle;
import android.util.Log;
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
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other, container, false);
        final ArrayList<ExampleItem> exampleList =  new ArrayList<>();
        //add all the options
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_battery, "Battery Calibartion", "calibrate your Battery!", "battery"));
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_build_24, "Build.prop Editor", "Edit the build.prop file of your phone", "build"));
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_memory_24, "CPU Info", "get Info about your CPU", "cpu"));
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_battery_unknown_24, "Battery Usage", "Check which apps are using your Battery", "usage"));
        //https://github.com/librespeed/speedtest-android
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_speed_24, "Speed Test", "Test your Internet Speed", "speed"));

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                exampleList.get(position);
                mAdapter.notifyItemChanged(position);
            }
        });


        Log.d("id",ExampleItem.getID());

        switch (ExampleItem.getID()){
            case "battery":
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BatteryCalibrationFragment()).commit();


                //ExampleItem.mText1 = "lol";
                break;
            case "build":
                break;

            case "speed":
                Log.d("Did it Work?", "yes it did");

        }

        return v;

    }




}

