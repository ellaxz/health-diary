package com.example.test1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.test1.R;
import com.example.test1.databinding.ActivityHomePageBinding;
import com.example.test1.viewmodel.PainRecordViewModel;

public class HomePage extends AppCompatActivity {

    private ActivityHomePageBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private PainRecordViewModel painRecordViewModel;

    public String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("EMAIL");

        painRecordViewModel = new ViewModelProvider(this).get(PainRecordViewModel.class);

        setSupportActionBar(binding.appBar.toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_pain_data_entry_fragment,
                R.id.nav_daily_rec_fragment,
                R.id.nav_report_fragment,
                R.id.nav_map_fragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController, mAppBarConfiguration);
    }
}