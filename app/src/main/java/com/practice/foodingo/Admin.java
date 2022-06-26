package com.practice.foodingo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class Admin extends Fragment {
    String userName, userPass;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin,container,false);

        //initialize elements
        EditText name = view.findViewById(R.id.adminUserName);
        EditText pass = view.findViewById(R.id.adminPassword);
        Button btn = view.findViewById(R.id.adminLogin);
        progressBar = view.findViewById(R.id.progressAdmin);
        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(v -> {
            //hide keyboard
            InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(btn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            userName = name.getText().toString().trim();
            userPass = pass.getText().toString().trim();

            if(TextUtils.isEmpty(userName)){
                name.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(userPass)){
                pass.setError("Required");
                return;
            }else if(pass.length()<8) {
                pass.setError("Invalid");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


            //logging in as admin
            auth.signInWithEmailAndPassword(userName,userPass).addOnSuccessListener(authResult -> {
                progressBar.setVisibility(View.GONE);
                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Intent intent = new Intent(getActivity(), EditMenuActivity.class);
                startActivity(intent);
                requireActivity().finish();


            }).addOnFailureListener(e -> {
                progressBar.setVisibility(View.INVISIBLE);
                requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getContext(), "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

        return view;
    }
}