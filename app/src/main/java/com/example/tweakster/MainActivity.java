package com.example.tweakster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.tweakster.Adapter.AppAdapter;
import com.example.tweakster.Model.AppInfo;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean mIncludeSystemApps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        listView = (ListView)findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        listView.setTextFilterEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshIt();
            }


        });





    }


    @Override
    protected void onResume() {
        super.onResume();
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }

    private void refreshIt() {
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }



    class LoadAppInfoTask extends AsyncTask<Integer,Integer, List<AppInfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<AppInfo> doInBackground(Integer... params) {

            List<AppInfo> apps = new ArrayList<>();
            PackageManager packageManager = getPackageManager();

            List<ApplicationInfo> infos = packageManager.getInstalledApplications(params[0]);


            for(ApplicationInfo info:infos){
                if(mIncludeSystemApps && (info.flags & ApplicationInfo.FLAG_SYSTEM) == 1){
                    continue;
                }

                AppInfo app = new AppInfo();
                app.info = info;
                app.label = (String) info.loadLabel(packageManager);
                apps.add(app);
            }
            //sort data
            return apps;
        }

        @Override
        protected void onPostExecute(List<AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            listView.setAdapter(new AppAdapter(MainActivity.this, appInfos));
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(listView, appInfos.size() + " Applications loaded", Snackbar.LENGTH_LONG).show();
        }
    }









    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_list:
                            selectedFragment = new ListFragment();
                            break;
                        case R.id.nav_other:
                            selectedFragment = new OtherFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}