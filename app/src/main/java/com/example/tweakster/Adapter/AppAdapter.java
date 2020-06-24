package com.example.tweakster.Adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweakster.ListFragment;
import com.example.tweakster.Model.AppInfo;
import com.example.tweakster.R;

import java.util.List;

public class AppAdapter extends ArrayAdapter<AppInfo> {

    public static Context mContext;
    LayoutInflater layoutInflater;
    PackageManager packageManager;
    List<AppInfo> apps;
    public AppAdapter(ListFragment context, List<AppInfo> apps) {
        super(mContext, R.layout.app_item_layout, apps);
        layoutInflater = LayoutInflater.from(mContext);
        packageManager = context.getActivity().getPackageManager();
        this.apps = apps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppInfo current = apps.get(position);
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.app_item_layout, parent, false);
        }

        TextView textViewTitle = (TextView)view.findViewById(R.id.titleTextView);
        textViewTitle.setText(current.label);

        try {

            PackageInfo packageInfo = packageManager.getPackageInfo(current.info.packageName, 0);

            if(!TextUtils.isEmpty(packageInfo.versionName)){
                String versionInfo = String.format("%s", packageInfo.versionName );
                TextView textVersion = (TextView)view.findViewById(R.id.versionId);
                textVersion.setText(versionInfo);
            }

            if(!TextUtils.isEmpty(current.info.packageName))
            {
                TextView textSubTitle = (TextView)view.findViewById(R.id.subTitle);
                textSubTitle.setText(current.info.packageName);
            }

        } catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.iconImage);
        Drawable background = current.info.loadIcon(packageManager);

        imageView.setBackground(background);

        return view;
    }
}
