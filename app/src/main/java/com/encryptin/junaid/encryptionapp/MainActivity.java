package com.encryptin.junaid.encryptionapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ecryptbtn=findViewById(R.id.encryptbtn);

        ecryptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File imginput=new File(Environment.getExternalStorageDirectory()+File.separator+"img.png");
                try {
                    new doEncrypt(MainActivity.this,imginput,"qwertyuiopasdfgh");
                    Toast.makeText(MainActivity.this,"fileSave AT= "+Environment.getExternalStorageDirectory(),Toast.LENGTH_LONG).show();
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        });

        Button decryptbtn=findViewById(R.id.decryptbtn);

        decryptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File imginput=new File(Environment.getExternalStorageDirectory()+File.separator+"encrypted.png");
                try {
                    new dodecrypt(MainActivity.this,imginput,"qwertyuiopasdfgh");
                    Toast.makeText(MainActivity.this,"fileSave AT= "+Environment.getExternalStorageDirectory(),Toast.LENGTH_LONG).show();
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
