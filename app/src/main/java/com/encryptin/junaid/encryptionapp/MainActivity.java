package com.encryptin.junaid.encryptionapp;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    JealousSky jealousSky=new JealousSky();
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    InputStream is;
    Uri picUri;
    File file,encfile;
    Encrypt_File ef;
    Passwords passwords;
    File op;
    ImageView getimgview;
    Button encryptin1,decryptin;
    byte[] fileBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getimgview=findViewById(R.id.getimgview);
        encryptin1=findViewById(R.id.encryptin);
        decryptin=findViewById(R.id.decryptin);


        getimgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });

        encryptin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Movies", "any");
                BufferedOutputStream bos = null;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] yourKey ="qwertyuiozxcvbnsdfghedfgh".getBytes();
                try {
                    byte[] filesBytes =ef.encodeFile(yourKey, ef.filetobyte(new File(filePath)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (bos != null) {
                        bos.flush();
                        bos.close();
                        bos.write(fileBytes);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    op = ef.bytetofile(fileBytes);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap bm=BitmapFactory.decodeFile(op.getAbsolutePath());
                getimgview.setImageBitmap(bm);

                /*String password=new Passwords().generateRandomPassword(32);
                ef = new Encrypt_File(new File(filePath));
                try {
                    ef.encodeFile(password.getBytes(),ef.getPlainstream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                encfile=ef.encryptedfile;
                getimgview.setImageBitmap();
                Toast.makeText(MainActivity.this,"qwertyuio",Toast.LENGTH_LONG).show();*/
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
