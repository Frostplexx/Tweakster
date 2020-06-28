package com.example.tweakster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.zip.CheckedOutputStream;


public class HomeFragment  extends Fragment {
    private FragHomeListener listener;
    private EditText editText;
    private Button buttonOk;
    private TextView textView;
    static public boolean isroot;
    static public boolean busyBox;
    String rooted;

    public interface FragHomeListener{
        void onInputHomeSent(CharSequence input);
    }
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_home, container, false);

    TextView root = (TextView)v.findViewById(R.id.root);
    TextView busyBoxtxt = (TextView)v.findViewById(R.id.busyBox);

        if (isroot){
            root.setText("rooted");
            root.setTextColor(Color.GREEN);
        } else {
            root.setText("unrooted");
            root.setTextColor(Color.RED);
        }


        if (busyBox){
            busyBoxtxt.setText("present");
            busyBoxtxt.setTextColor(Color.GREEN);
        } else {
            busyBoxtxt.setText("not present");
            busyBoxtxt.setTextColor(Color.RED);
        }



        ((TextView)v.findViewById(R.id.OS)).setText(android.os.Build.VERSION.RELEASE + " (" + android.os.Build.VERSION.SDK + ")");
        ((TextView)v.findViewById(R.id.informationdevicemodel)).setText(android.os.Build.MODEL);
        ((TextView)v.findViewById(R.id.informationdevice)).setText(android.os.Build.DEVICE);
        return v;
    }







    public static Fragment isRooted(boolean b) {
        if (b) {
            isroot = true;
        }
        return new HomeFragment();
    }

    public static Fragment hasBusyBox(boolean b) {
        if (b) {
            busyBox = true;
        }
        return new HomeFragment();
    }
}
