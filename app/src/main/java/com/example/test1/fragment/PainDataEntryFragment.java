package com.example.test1.fragment;




import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.test1.HomePage;
import com.example.test1.R;
import com.example.test1.databinding.PainDataEntryFragmentBinding;

import com.example.test1.entity.PainRecord;

import com.example.test1.viewmodel.PainRecordViewModel;

import java.util.ArrayList;
import java.util.List;


public class PainDataEntryFragment extends Fragment {
    private PainDataEntryFragmentBinding binding;
    private Button btnSend;
    private EditText input;
    private PainRecordViewModel painRecordViewModel;
    private PainRecord painRecord;
    private RadioGroup radioGroup;
    private RadioButton radioButton;




    public PainDataEntryFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = PainDataEntryFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        super.onViewCreated(view, savedInstanceState);
        painRecordViewModel = new ViewModelProvider(requireActivity()).get(PainRecordViewModel.class);
        painRecordViewModel.getAllRecords().observe(getViewLifecycleOwner(), list -> {
            // Update the list UI
        });

        HomePage homePage = (HomePage) getActivity();
        String email = homePage.email;


        final String[] arr = {"Head","Back","legs","arms"};
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arr);
        binding.painLocationSpinner.setAdapter(adapter);



        final String[] abb = {"low","high","average"};
        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,abb);
        binding.moodSpinner.setAdapter(adapter2);



        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = binding.editTextGoal.getText().toString();
                float painful = binding.slider.getValue();
                //String mood = binding.radioGroup.toString();
                String mood = binding.moodSpinner.getSelectedItem().toString();
                String painLocation = binding.painLocationSpinner.getSelectedItem().toString();



                //binding.slider.setEnabled(false);

                if(!message.isEmpty()){

                    double painLevel = Double.parseDouble(String.valueOf(painful));
                    PainRecord painRecord = new PainRecord(email, painLevel,message,mood,painLocation);
                    PainRecordViewModel.insert(painRecord);


                }
            }
        });


        return view;
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    private void setContentView(View view) {
    }
}

