package com.practice.foodingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.practice.foodingo.Support_Files.ReusableCodeForAll;

import java.util.Objects;

public class SignInCustomer extends AppCompatActivity {
    TextInputLayout mailIn, passIn;
    String email,password;
    Button signIn;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_customer);
        mailIn = findViewById(R.id.signInMail);
        passIn = findViewById(R.id.signInPassword);
        progressBar = findViewById(R.id.progressSignIn);
        signIn = findViewById(R.id.signInBtnCust);
        auth = FirebaseAuth.getInstance();

        progressBar.bringToFront();

        signIn.setOnClickListener(v->{
            email = Objects.requireNonNull(mailIn.getEditText()).getText().toString().trim();
            password = Objects.requireNonNull(passIn.getEditText()).getText().toString().trim();

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(signIn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);


            if(checkError()){
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener((Runnable)->{
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Intent intent = new Intent(SignInCustomer.this, HomeMenuActivity.class);
                    startActivity(intent);
                    finish();

                }).addOnFailureListener((Runnable)->{
                    progressBar.setVisibility(View.INVISIBLE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    ReusableCodeForAll.ShowAlert(SignInCustomer.this, "ERROR", Runnable.getMessage());

                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottm, R.anim.slide_out_top);
    }

    private boolean checkError() {
        boolean mailError = false;
        boolean passError = false;
        mailIn.setError("");
        passIn.setError("");
        mailIn.setErrorEnabled(false);
        mailIn.setErrorEnabled(false);

        if(TextUtils.isEmpty(email)){
            mailIn.setErrorEnabled(true);
            mailIn.setError("Required");
        }else{
            mailError = true;
        }

        if(TextUtils.isEmpty(password)){
            passIn.setErrorEnabled(true);
            passIn.setError("Required");
        }else{
            passError = true;
        }

        return passError && mailError;
    }
}