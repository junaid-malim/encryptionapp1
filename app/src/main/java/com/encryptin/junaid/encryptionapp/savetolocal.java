package com.encryptin.junaid.encryptionapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

public class savetolocal {

    private String mypreference="mypref";
    SharedPreferences sharedPreferences;

    public savetolocal(String phoneno,Context context){
        sharedPreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("Phonekey",phoneno);
        editor.putString("Uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
        editor.apply();

    }

    public savetolocal(){
    }

    public String returnphoneno(){
        return sharedPreferences.getString("PhoneKey","null");
    }

    public String returnuid(){
        return sharedPreferences.getString("Uid","null");
    }

}
