package com.example.tweakster;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;
import java.util.logging.Handler;

public class ExampleItem extends Fragment {
    private static String tmp;
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ExampleItem(int imageResource, String text1, String text2, String id){
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        tmp = id;

    }

    public static String getID(){
        return tmp;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getText1(){
        return mText1;
    }

    public String getText2(){
        return mText2;
    }


}
