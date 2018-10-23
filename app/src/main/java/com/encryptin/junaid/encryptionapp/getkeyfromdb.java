package com.encryptin.junaid.encryptionapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getkeyfromdb {

    DatabaseReference db;

    public getkeyfromdb(final Context context, final String tokenid) {

        db = FirebaseDatabase.getInstance().getReference("keyslist").child(new savetolocal().returnphoneno(context));
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new savetolocal().setkeyonecive(context,dataSnapshot.child(tokenid).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
