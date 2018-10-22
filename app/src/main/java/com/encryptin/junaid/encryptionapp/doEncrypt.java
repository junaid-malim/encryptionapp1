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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class doEncrypt {
    Date date = new Date();
    String filename,fileTypeset;
    String key;
    String filepathofenc;

    public doEncrypt(){}

    public doEncrypt(Context context, File file,String key,String fileType) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {

        DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmms");
        String strDate = dateFormat.format(date);

        String saveencryptedto=Environment.getExternalStorageDirectory()+File.separator+"Encryptedfiles"+File.separator;

        filename = "Encrypted_"+strDate;

        fileTypeset=fileType;
        filepathofenc=saveencryptedto+filename+fileType;

        if(!(new File(saveencryptedto)).exists()) {
            new File(saveencryptedto).mkdir();
        }

        FileInputStream fis=new FileInputStream(file);

        FileOutputStream fos=new FileOutputStream(saveencryptedto+filename+"."+fileType);

        SecretKeySpec sks=new SecretKeySpec(key.getBytes(),"AES");

        Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,sks,new IvParameterSpec(
                new byte[cipher.getBlockSize()]));

        CipherOutputStream cos=new CipherOutputStream(fos,cipher);

        int b;
        byte[] d=new byte[8];
        while ((b=fis.read(d))!=-1){
            cos.write(d,0,b);
        }
        cos.flush();
        cos.close();
        fis.close();

        Toast.makeText(context,"encrypted",Toast.LENGTH_LONG).show();

    }

    public String getKey() {
        return key;
    }

    public String getTokenid() {
        return filename+"."+fileTypeset;
    }

    public String getFilepathofenc() {
        return filepathofenc;
    }
}
