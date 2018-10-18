package com.encryptin.junaid.encryptionapp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt_File {

    int fsize;
    byte[] encryptedbyte;
    File encryptedfile;
    byte[] decryptedbyte;
    File decryptedfile;
    public String type;
    byte[] plainstream;

    public Encrypt_File(File filedata) {

        plainstream = filetobyte(filedata);
        //hashCode(key,Passwords.getNextSalt())

    }

    public byte[] filetobyte(File file){
        type=surffixof(file.getName());
        fsize=(int)file.length();
        byte[] bytes=new byte[fsize];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    private String surffixof(String name) {
        if(name == null || name.equals("")){
            return "";
        }
        String suffix = "";
        int index = name.lastIndexOf(".");
        if (index != -1 ) {
            suffix = name.substring(index + 1);
        }
        return suffix;
    }

    public File bytetofile(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        File fileFromBytes = (File) ois.readObject();
        bis.close();
        ois.close();

        return fileFromBytes;
    }

    public byte[] encodeFile(byte[] key, byte[] fileData) throws Exception
    {

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        return cipher.doFinal(fileData);
    }

    public byte[] decodeFile(byte[] key, byte[] fileData) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        encryptedfile=bytetofile(cipher.doFinal(fileData));
        return cipher.doFinal(fileData);
    }

    public byte[] getEncryptedbyte() {
        return encryptedbyte;
    }

    public byte[] getDecryptedbyte() {
        return decryptedbyte;
    }

    public File getEncryptedfile(){
        try {
            bytetofile(getEncryptedbyte());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return encryptedfile;
    }

    public File getDecryptedfile(){
        return decryptedfile;
    }

    public void setEncryptedbyte(byte[] encryptedbyte) {
        this.encryptedbyte = encryptedbyte;
    }

    public void setEncryptedfile(File encryptedfile) {
        this.encryptedfile = encryptedfile;
    }

    public void setDecryptedbyte(byte[] decryptedbyte) {
        this.decryptedbyte = decryptedbyte;
    }

    public void setDecryptedfile(File decryptedfile) {
        this.decryptedfile = decryptedfile;
    }

    public byte[] getPlainstream() {
        return plainstream;
    }

    public void setPlainstream(byte[] plainstream) {
        this.plainstream = plainstream;
    }
}
