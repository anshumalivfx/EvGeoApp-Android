package com.anshumali.evgeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SignInPage extends AppCompatActivity {

    private EditText phoneNumber;
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
        signinButton = findViewById(R.id.buttonSendOTP);
        ccp = findViewById(R.id.ccp);
        progressBar = findViewById(R.id.progressBar);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.getText().toString().matches("[0-9]+")){
                    ccp.registerCarrierNumberEditText(phoneNumber);
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
                                    signinButton.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    signinButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    signinButton.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), OTPPage.class);
                                    intent.putExtra("mobile", ccp.getFormattedFullNumber().toString());
                                    intent.putExtra("resendMobile", ccp.getFullNumberWithPlus().toString());
                                    intent.putExtra("verificationId", s);

                                    startActivity(intent);
                                }
                            }
                    );

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