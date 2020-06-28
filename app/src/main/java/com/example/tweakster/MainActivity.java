package com.example.tweakster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tweakster.Adapter.AppAdapter;
import com.example.tweakster.Model.AppInfo;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.sufficientlysecure.rootcommands.RootCommands;
import org.sufficientlysecure.rootcommands.Shell;
import org.sufficientlysecure.rootcommands.command.SimpleCommand;

import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // start root shell
        try {
            Shell shell = Shell.startRootShell();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();

        if (RootCommands.rootAccessGiven()) {
            transaction.replace(R.id.fragment_container,
                    HomeFragment.isRooted(true)).commit();
        } else {
            transaction.replace(R.id.fragment_container,
                    HomeFragment.isRooted(false)).commit();
        }
        SimpleCommand command1 = new SimpleCommand("toolbox ls");
        if(new SimpleCommand("test -h /bin/ls && test `readlink /bin/ls` = busybox") == true){
            
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