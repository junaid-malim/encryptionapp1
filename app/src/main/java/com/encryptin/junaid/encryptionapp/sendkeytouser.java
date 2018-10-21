package com.encryptin.junaid.encryptionapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendkeytouser {

    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("keyslist");

    public sendkeytouser(final Context context, String tothisuserphno, String key) {

        databaseReference.child(tothisuserphno).setValue(key).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"key Tranmitted...",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"key not Transmitted!",Toast.LENGTH_LONG).show();
            }
        });

    }
}
