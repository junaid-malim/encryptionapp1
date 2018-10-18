package com.encryptin.junaid.encryptionapp;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    JealousSky jealousSky=new JealousSky();
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    InputStream is;
    Uri picUri;

    ImageView getimgview;
    Button encryptin1,decryptin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getimgview=findViewById(R.id.getimgview);
        encryptin1=findViewById(R.id.encryptin);
        decryptin=findViewById(R.id.decryptin);

        try {
            jealousSky.initialize("longestPasswordEverCreatedInAllTheUniverseOrMore", "FFD7BADF2FBB1999");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        getimgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });

        encryptin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    is =getContentResolver().openInputStream(picUri);
                    File f1=jealousSky.encryptToFile(is,"lol.jpg");
                    getimgview.setImageBitmap(BitmapFactory.decodeFile(f1.getPath()));
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | InvalidKeySpecException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"qwertyuio",Toast.LENGTH_LONG).show();
            }
        });


        decryptin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){

                picUri = data.getData();

                filePath = getPath(picUri);

                if (picUri != null) {
                    Log.d("picUri", picUri.toString());
                }
                else {
                    Toast.makeText(MainActivity.this, "nulla", Toast.LENGTH_LONG).show();
                }
                Log.d("filePath", filePath);
                getimgview.setImageURI(picUri);

            }

        }

    }
    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        else{
            Toast.makeText(MainActivity.this,"coloumn nulla",Toast.LENGTH_LONG).show();
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
