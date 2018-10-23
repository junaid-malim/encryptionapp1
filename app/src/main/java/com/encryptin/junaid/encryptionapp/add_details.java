package com.encryptin.junaid.encryptionapp;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


class add_details {

    DatabaseReference database=FirebaseDatabase.getInstance().getReference("users");
    String uid;

    public add_details(final Context context, String phoneNumber,String name) {

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        database.child(uid).child("phonenumber").setValue(phoneNumber);
        database.child(uid).child("name").setValue(name);

    }
}
