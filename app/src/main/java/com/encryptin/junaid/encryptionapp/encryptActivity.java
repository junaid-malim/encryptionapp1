package com.encryptin.junaid.encryptionapp;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import javax.crypto.NoSuchPaddingException;

public class encryptActivity extends AppCompatActivity {
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;
    Uri picUri;
    String Filetype;
    File toenc;
    doEncrypt objenc;
    String password=new Passwords().generateRandomPassword(16);

    TextView fileselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        Button encryption=findViewById(R.id.startencryption);
        Button Browse=findViewById(R.id.browse);
        Button sharebtn=findViewById(R.id.sharebtn);

        fileselected = findViewById(R.id.fileselected);

        Browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileBrowse();
            }
        });

        encryption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    objenc = new doEncrypt(encryptActivity.this,toenc,password,Filetype);

                } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,FileProvider.getUriForFile(getApplicationContext(),"com.mydomain.fileprovider",new File(objenc.getFilepathofenc())));
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("application/octet-stream");
                startActivityForResult(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)),1001);


            }
        });

    }

    String getFileExtention(String name) {
        if(name == null || name.equals("")){
            return "";
        }
        String fileextention = "";
        int index = name.lastIndexOf(".");
        if (index != -1 ) {
            fileextention = name.substring(index + 1);
        }
        return fileextention;
    }


    private void FileBrowse() {
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
                    Toast.makeText(encryptActivity.this, "null", Toast.LENGTH_LONG).show();
                }
                Log.d("filePath", filePath);

                Filetype=getFileExtention(filePath);

                toenc = new File(filePath);

                fileselected.setVisibility(View.VISIBLE);
                fileselected.setText(MessageFormat.format("{0}\n{1}", toenc.getName(), toenc.getPath()));
            }

            if (requestCode==1001){
                Intent intent=new Intent(encryptActivity.this,contactlistActivity.class);
                intent.putExtra("key",password);
                intent.putExtra("tokenid",objenc.getTokenid());
                startActivity(intent);
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
            Toast.makeText(encryptActivity.this,"coloumn null",Toast.LENGTH_LONG).show();
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


}
