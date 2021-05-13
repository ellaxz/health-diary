package com.example.test1.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.test1.HomePage;
import com.example.test1.databinding.DailyRecordFragmentBinding;
import com.example.test1.entity.PainRecord;
import com.example.test1.viewmodel.PainRecordViewModel;

import java.util.List;

public class DailyRecordFragment extends Fragment {
    private DailyRecordFragmentBinding addBinding;
    PainRecordViewModel painRecordViewModel;

    public DailyRecordFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = DailyRecordFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();

        painRecordViewModel = new ViewModelProvider(requireActivity()).get(PainRecordViewModel.class);

        HomePage homePage = (HomePage) getActivity();
        String email =  homePage.email;

        painRecordViewModel.getRecordFromEmail(email).observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                if (painRecords.size() > 0) {
                    PainRecord lastUser = painRecords.get(painRecords.size() - 1);
                    Log.e("DAILYRECORD", lastUser.email + ", pain level=" + lastUser.painLevel+ "mood" + lastUser.mood + "goal" + lastUser.message);
                    addBinding.textView5.setText(lastUser.email + ", pain level=" + lastUser.painLevel + "mood" + lastUser.mood + "goal" + lastUser.message);
                }
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