package com.encryptin.junaid.encryptionapp;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class testActivity extends AppCompatActivity {

    TextView thetxt1,thetxt2;
    ImageView imageView1=findViewById(R.id.imageView11),imageView2=findViewById(R.id.imageView22);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
findViewById(R.id.dfa).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        try {
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.sig);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitMapData = stream.toByteArray();

            byte[] encData = encrypt("keykey".getBytes(), bitMapData);
            imageView1.setImageBitmap(BitmapFactory.decodeByteArray(encData,0,encData.length));
            byte[] decData= decrypt("keykey",encData);
            imageView1.setImageBitmap(BitmapFactory.decodeByteArray(decData,0,decData.length));

        } catch (Exception e) {
        e.printStackTrace();
    }

}

});

    }

    private byte[] encrypt(byte[] key, byte[] clear) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(key);

        SecretKeySpec skeySpec = new SecretKeySpec(digestOfPassword, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }
    private byte[] decrypt(String key, byte[] encrypted) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(key.getBytes());

        SecretKeySpec skeySpec = new SecretKeySpec(digestOfPassword, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
}
