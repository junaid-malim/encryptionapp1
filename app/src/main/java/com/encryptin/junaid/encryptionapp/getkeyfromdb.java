package com.encryptin.junaid.encryptionapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class getkeyfromdb {

    DatabaseReference db=FirebaseDatabase.getInstance().getReference("keyslist").child(new savetolocal().returnphoneno());

    public getkeyfromdb() {



    }
}
