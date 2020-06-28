package com.example.tweakster;

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
    static public boolean test;
    String rooted;

    public interface FragHomeListener{
        void onInputHomeSent(CharSequence input);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_home, container, false);

    TextView root = (TextView)v.findViewById(R.id.root);

        if (test){
            root.setText("rooted");
            root.setTextColor(Color.GREEN);
        } else {
            root.setText("unrooted");
            root.setTextColor(Color.RED);
        }


        return v;
    }







    public static Fragment isRooted(boolean b) {
        if (b) {
            test = true;
        }
        return new HomeFragment();
    }
}
