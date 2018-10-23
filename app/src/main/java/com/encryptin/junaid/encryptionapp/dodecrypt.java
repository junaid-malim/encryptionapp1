package com.encryptin.junaid.encryptionapp;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class dodecrypt {
    public dodecrypt(Context context,File file,String key,String filetype) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {

        FileInputStream fis=new FileInputStream(file);

        FileOutputStream fos=new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"Decrypted"+"."+filetype);
        SecretKeySpec sks=new SecretKeySpec(key.getBytes(),"AES");

        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,sks,new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        CipherInputStream cis=new CipherInputStream(fis,cipher);
        int b;
        byte[] d=new byte[8];
        while((b=cis.read(d))!=-1){
            fos.write(d,0,b);
        }
        fos.flush();
        fos.close();
        fis.close();

        Toast.makeText(context,"Decrypted",Toast.LENGTH_LONG).show();

    }
}
