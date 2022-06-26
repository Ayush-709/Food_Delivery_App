package com.practice.foodingo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.foodingo.Support_Files.ReusableCodeForAll;

import java.util.HashMap;
import java.util.Objects;

public class RegisterCustomer extends AppCompatActivity {
    TextInputLayout inputName, inputPass, inputMail, inputConfirm;
    Button regBtn;
    String name, password, email, confirm;
    FirebaseDatabase database;
    ProgressBar progressBar;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
        inputMail = findViewById(R.id.regCustMail);
        inputConfirm = findViewById(R.id.regCustConfPass);
        inputName = findViewById(R.id.regCustName);
        inputPass = findViewById(R.id.regCustPassword);
        regBtn = findViewById(R.id.regBtnCust);
        progressBar = findViewById(R.id.progressReg);
        progressBar.bringToFront();

        database =FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        regBtn.setOnClickListener(v->{
            name = Objects.requireNonNull(inputName.getEditText()).getText().toString().trim();
            email = Objects.requireNonNull(inputMail.getEditText()).getText().toString().trim();
            password = Objects.requireNonNull(inputPass.getEditText()).getText().toString().trim();
            confirm = Objects.requireNonNull(inputConfirm.getEditText()).getText().toString().trim();

            //hide keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(regBtn.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

            if(noError()){
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        String userid =name+"_"+ Objects.requireNonNull(auth.getCurrentUser()).getUid();
                        reference = database.getReference("customers").child(userid);
                        final HashMap<String, String> map = new HashMap<>();
                        map.put("name",name);
                        map.put("email", email);
                        map.put("password", password);
                        reference.setValue(map).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterCustomer.this);
                                builder.setMessage("You have successfully Registered!");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Ok", (dialog, which) -> {
                                    dialog.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Intent intent = new Intent(RegisterCustomer.this, HomeMenuActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                });

                                AlertDialog alert = builder.create();
                                alert.show();


                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                ReusableCodeForAll.ShowAlert(RegisterCustomer.this, "Error", Objects.requireNonNull(task1.getException()).getMessage());

                            }

                        });



                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(RegisterCustomer.this, "Error "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    private boolean noError() {

        boolean noNameError=false;
        boolean noMailError=false;
        boolean noPassError=false;
        boolean noConfirmError=false;
        String pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        //initial no error

        inputName.setErrorEnabled(false);
        inputName.setError("");
        inputMail.setErrorEnabled(false);
        inputMail.setError("");
        inputConfirm.setErrorEnabled(false);
        inputConfirm.setError("");
        inputPass.setErrorEnabled(false);
        inputPass.setError("");


        //name error check
        if(TextUtils.isEmpty(name)){
            inputName.setErrorEnabled(true);
            inputName.setError("Required");
        }else if(name.length()>=3){
            noNameError = true;
        }else{
            inputName.setErrorEnabled(true);
            inputName.setError("Name must be atleast 3 letters");
        }

        //password error check
        if(TextUtils.isEmpty(password)){
            inputPass.setErrorEnabled(true);
            inputPass.setError("Required");
        }else if(password.length()>6){
            noPassError = true;
        }else{
            inputPass.setErrorEnabled(true);
            inputPass.setError("Password too weak");
        }

        //email error check
        if(TextUtils.isEmpty(email)){
            inputMail.setErrorEnabled(true);
            inputMail.setError("Required");
        }else if(email.matches(pattern)){
            noMailError = true;
        }else{
            inputMail.setErrorEnabled(true);
            inputMail.setError("Invalid Email Address");
        }

        //confirm error check
        if(TextUtils.isEmpty(confirm)){
            inputConfirm.setErrorEnabled(true);
            inputConfirm.setError("Required");
        }else if(confirm.equals(password)){
            noConfirmError = true;
        }else{
            inputConfirm.setErrorEnabled(true);
            inputConfirm.setError("Password does not matches");
        }


        return noConfirmError && noMailError && noNameError && noPassError;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottm, R.anim.slide_out_top);
    }
}