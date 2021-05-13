package com.example.test1.fragment;




import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.test1.HomePage;
import com.example.test1.R;
import com.example.test1.databinding.PainDataEntryFragmentBinding;

import com.example.test1.entity.PainRecord;

import com.example.test1.viewmodel.PainRecordViewModel;



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


        //RadioGroup radioGroup = (RadioGroup) homePage.findViewById(R.id.radioGroup);
//        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
//        RadioButton radioButton = binding.radioGroup.findViewById(selectedId);




        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = binding.editTextGoal.getText().toString();
                float painful = binding.slider.getValue();
                String mood = binding.radioGroup.toString();

                //binding.slider.setEnabled(false);

                if(!message.isEmpty()){

                    double painLevel = Double.parseDouble(String.valueOf(painful));
                    PainRecord user = new PainRecord(email, painLevel,message,mood);
                    PainRecordViewModel.insert(painRecord);


                }
            }
        });









        return view;


//        List<String> list = new ArrayList<String>();
//        list.add("Shoulder");
//        list.add("Head");
//        list.add("Back");
//        final ArrayAdapter<String> spinnerAdapter = new
//                ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);
//        binding.movieSpinner.setAdapter(spinnerAdapter);

        //return rootView;


    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    private void setContentView(View view) {
    }
}

