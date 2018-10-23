package com.encryptin.junaid.encryptionapp;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//ADD Flow//////////ADD flow/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignInActivity extends AppCompatActivity {

    final static String TAG="phone mAuth";

    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    EditText nameget,phonenoget,codenoget;
    Button sendcodebtn,donebtn,resendcodebtn;
    Button countdowmtmr,disableddone;

    String codesent;

    String phoneNumber;
    PhoneAuthProvider.ForceResendingToken mResendtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        nameget=findViewById(R.id.nameget);
        phonenoget=findViewById(R.id.phonenoget);
        codenoget=findViewById(R.id.codenoget);
        sendcodebtn=findViewById(R.id.sendcodebtn);
        donebtn=findViewById(R.id.donebtn);
        countdowmtmr=findViewById(R.id.countdowntmr);
        resendcodebtn=findViewById(R.id.resendcodebtn);
        disableddone=findViewById(R.id.disableddone);

        codenoget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                disableddone.setVisibility(View.VISIBLE);
                donebtn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(codenoget.getText().length()<6||codenoget.getText().length()>6){
                    disableddone.setVisibility(View.VISIBLE);
                    donebtn.setVisibility(View.INVISIBLE);
                }
                else{
                    disableddone.setVisibility(View.INVISIBLE);
                    donebtn.setVisibility(View.VISIBLE);
                }

            }
        });


        sendcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendverificationcode();
                disableddone.setVisibility(View.VISIBLE);
            }
        });

        resendcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendverificationcode(mResendtoken);
            }
        });

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codenoget.getText().toString().isEmpty())
                {
                    Toast.makeText(SignInActivity.this,"enter code first-",Toast.LENGTH_LONG).show();
                }
                verifySignInCode();
            }
        });

    }



    private void verifySignInCode() {
        String code=codenoget.getText().toString();

        if(code.length()<=0||code.length()<6||code.length()>6){

            Toast.makeText(SignInActivity.this,"Please Enter 6 digit code",Toast.LENGTH_LONG).show();
            codenoget.requestFocus();
            return;

        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            new add_details(SignInActivity.this,phoneNumber,nameget.getText().toString());
                            Intent intnt=new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intnt);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                            new savetolocal(nameget.getText().toString(),phoneNumber,SignInActivity.this);

                        } else {

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignInActivity.this,"enter correct code",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private void sendverificationcode() {

        phoneNumber=phonenoget.getText().toString();

        if(nameget.getText().toString()!=null){
            Toast.makeText(getApplicationContext(),"Enter name so thats ur friend reconize u",Toast.LENGTH_LONG).show();
        }

        if(phoneNumber.isEmpty()) {
            Toast.makeText(SignInActivity.this,"PLEASE ENTER A PHONE NO.",Toast.LENGTH_LONG).show();
            return;
        }

        if(phoneNumber.length()<10||phoneNumber.length()>10){
            Toast.makeText(SignInActivity.this,"PLEASE ENTER A VALID PHONE NO.",Toast.LENGTH_LONG).show();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
        sendcodebtn.setVisibility(View.GONE);
        countdowmtmr.setVisibility(View.VISIBLE);
        codenoget.setVisibility(View.VISIBLE);
        codenoget.requestFocus();

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdowmtmr.setText("Resend Code: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdowmtmr.setVisibility(View.GONE);
                resendcodebtn.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void resendverificationcode(PhoneAuthProvider.ForceResendingToken token) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);

        resendcodebtn.setVisibility(View.GONE);
        countdowmtmr.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdowmtmr.setText("Resend Code: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdowmtmr.setVisibility(View.GONE);
                resendcodebtn.setVisibility(View.VISIBLE);
            }
        }.start();


    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codesent=s;
            mResendtoken=forceResendingToken;
        }
    };

}
