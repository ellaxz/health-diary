package com.example.test1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test1.MainActivity;
import com.example.test1.databinding.HomeFragmentBinding;

import com.example.test1.viewmodel.PainRecordViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private PainRecordViewModel model;
    private HomeFragmentBinding addBinding;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = HomeFragmentBinding.inflate(inflater, container, false);


        View view = addBinding.getRoot();
        PainRecordViewModel painRecordViewModel = new ViewModelProvider(requireActivity()).get(PainRecordViewModel.class);
        addBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }
}