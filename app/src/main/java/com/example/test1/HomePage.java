package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.test1.databinding.ActivityHomePageBinding;
import com.example.test1.entity.PainRecord;
import com.example.test1.fragment.DailyRecordFragment;
import com.example.test1.fragment.HomeFragment;
//import com.example.test1.fragment.MapFragment;
import com.example.test1.fragment.PainDataEntryFragment;
import com.example.test1.fragment.ReportFragment;
import com.example.test1.viewmodel.PainRecordViewModel;

import java.util.List;

public class HomePage extends AppCompatActivity{
    private ActivityHomePageBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private PainRecordViewModel painRecordViewModel;
    FragmentManager fragmentManager = getSupportFragmentManager();

    DailyRecordFragment dailyRecordFragment;
    HomeFragment homeFragment;
    //MapFragment mapFragment;
    PainDataEntryFragment painDataEntryFragment;
    ReportFragment reportFragment;

    public String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        email = getIntent().getStringExtra("EMAIL");

        painRecordViewModel = ViewModelProviders.of(this).get(PainRecordViewModel.class);
        painRecordViewModel.getAllRecords().observe(this, new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                //upddate recyclerView
                Toast.makeText(HomePage.this, "onChanged", Toast.LENGTH_SHORT).show();

            }
        });


        setSupportActionBar(binding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_fragment,
                R.id.nav_pain_data_entry_fragment,
                R.id.nav_daily_rec_fragment, R.id.nav_report_fragment,R.id.nav_map_fragment)
//to display the Navigation button as a drawer symbol,not being shown as an Up button
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
//Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Sets up a Toolbar for use with a NavController
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController,
                mAppBarConfiguration);

    }

    private void replaceFragment(DailyRecordFragment dailyRecordFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_container, dailyRecordFragment);
        fragmentTransaction.commit();
    }



}