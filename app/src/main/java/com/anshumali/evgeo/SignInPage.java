package com.anshumali.evgeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SignInPage extends AppCompatActivity {

    private EditText phoneNumber, otpBlock;
    private Button signinButton;
    private CountryCodePicker ccp;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        mAuth = FirebaseAuth.getInstance();
        phoneNumber = findViewById(R.id.phoneNumber);
        signinButton = findViewById(R.id.signin);
        ccp = findViewById(R.id.countryCode_picker);
        otpBlock = findViewById(R.id.otpBlock);
        progressBar = findViewById(R.id.progressBar);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.getText().toString().matches("[0-9]+")){
                    otpBlock.setVisibility(View.VISIBLE);
                    phoneNumber.setFocusable(false);
                    ccp.registerCarrierNumberEditText(phoneNumber);
                    signinButton.setText("SignIn");
                    progressBar.setVisibility(View.VISIBLE);
                    signinButton.setVisibility(View.GONE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            ccp.getFullNumberWithPlus().toString(),
                            60,
                            TimeUnit.SECONDS,
                            SignInPage.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }
                    );
                    signinButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "SignIn" + ccp.getFullNumberWithPlus(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "FUCK YOU BRO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signIn(){

    }
}