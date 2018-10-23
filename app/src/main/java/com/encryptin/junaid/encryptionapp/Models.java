package com.encryptin.junaid.encryptionapp;

public class Models {

    String usname,phno;

    public Models(String usname, String phno) {
        this.usname = usname;
        this.phno = phno;
    }


    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }
}
