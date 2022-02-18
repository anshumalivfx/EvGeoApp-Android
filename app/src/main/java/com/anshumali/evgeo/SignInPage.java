package com.anshumali.evgeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class SignInPage extends AppCompatActivity {

    private EditText phoneNumber, otpBlock;
    private Button signinButton;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        phoneNumber = findViewById(R.id.phoneNumber);
        signinButton = findViewById(R.id.signin);
        ccp = findViewById(R.id.countryCode_picker);
        otpBlock = findViewById(R.id.otpBlock);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.getText().toString().matches("[0-9]+")){
                    otpBlock.setVisibility(View.VISIBLE);
                    phoneNumber.setFocusable(false);
                    ccp.registerCarrierNumberEditText(phoneNumber);
                    signinButton.setText("SignIn");
                    signinButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "SignIn" + ccp.getFormattedFullNumber(), Toast.LENGTH_SHORT).show();

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