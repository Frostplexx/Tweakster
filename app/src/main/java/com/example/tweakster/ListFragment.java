package com.example.tweakster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tweakster.Adapter.AppAdapter;
import com.example.tweakster.Model.AppInfo;
import com.google.android.material.snackbar.Snackbar;

import org.sufficientlysecure.rootcommands.Shell;
import org.sufficientlysecure.rootcommands.command.SimpleCommand;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ListFragment extends Fragment {

    private FragListListener listener;
    private EditText editText;
    private Button buttonOk;


    private ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean mIncludeSystemApps;

    public void updateEditText(CharSequence input) {
    }

    public interface FragListListener {
        void onInputListSent(CharSequence input);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        AppAdapter.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) v.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);
        listView.setTextFilterEnabled(true);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int postition, long id) {

             // start root shell

             Shell shell = null;
             try {
                 shell = Shell.startRootShell();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             AppInfo app = (AppInfo)parent.getItemAtPosition(postition);

             if(app.info.packageName == null){
                 Toast.makeText(getActivity().getApplicationContext(), "can not find package name", Toast.LENGTH_SHORT).show();
             } else {

                 SimpleCommand perm = new SimpleCommand("pm grant " + app.info.packageName + " android.permission.WRITE_SECURE_SETTINGS");

                 try {
                     shell.add(perm).waitForFinish();
                 } catch (TimeoutException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

                 Toast.makeText(getActivity().getApplicationContext(),  app.info.packageName + " was given \"write Secure Settings\" Permission", Toast.LENGTH_SHORT).show();
                 // close root shell
                 try {
                     shell.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
     });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshIt();
            }


        });
        return v;
    }
//App List Code


    private void refreshIt() {
        ListFragment.LoadAppInfoTask loadAppInfoTask = new ListFragment.LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }


    class LoadAppInfoTask extends AsyncTask<Integer, Integer, List<AppInfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<AppInfo> doInBackground(Integer... params) {

            List<AppInfo> apps = new ArrayList<>();
            PackageManager packageManager = getActivity().getPackageManager();

            List<ApplicationInfo> infos = packageManager.getInstalledApplications(params[0]);


            for (ApplicationInfo info : infos) {
                if (mIncludeSystemApps && (info.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    continue;
                }

                AppInfo app = new AppInfo();
                app.info = info;
                app.label = (String) info.loadLabel(packageManager);
                apps.add(app);
            }
            //sort data

            Collections.sort(apps, new DNComparator());
            return apps;
        }

        @Override
        protected void onPostExecute(List<AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            listView.setAdapter(new AppAdapter(ListFragment.this, appInfos));
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(listView, appInfos.size() + " Applications loaded", Snackbar.LENGTH_LONG).show();
        }


    }

    private class DNComparator implements Comparator<AppInfo> {

        @Override
        public int compare(AppInfo aa, AppInfo ab) {
            CharSequence sa = aa.label;
            CharSequence sb = ab.label;

            if(sa == null){
                sa = aa.info.packageName;
            }
            if(sb == null){
                sb = ab.info.packageName;
            }
            return Collator.getInstance().compare(sa.toString(), sb.toString());
        }
    }
}
