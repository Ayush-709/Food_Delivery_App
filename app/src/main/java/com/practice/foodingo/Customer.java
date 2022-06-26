package com.practice.foodingo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Customer extends Fragment {

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_customer, container,false);
        Button btn = view.findViewById(R.id.registerBtnHomeScreen);
        TextView tv = view.findViewById(R.id.signInHomeScreen);

        btn.setOnClickListener(v ->{
            startActivity(new Intent(getActivity(), RegisterCustomer.class));
            requireActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

        });

        tv.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignInCustomer.class));
            requireActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
        });
        return view;
    }

}