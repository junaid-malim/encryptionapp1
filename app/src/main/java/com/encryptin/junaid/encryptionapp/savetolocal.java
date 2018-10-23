package com.encryptin.junaid.encryptionapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

public class savetolocal {

    private String mypreference="mypref";
    SharedPreferences sharedPreferences;


    public savetolocal(String name,String phoneno,Context context){
        sharedPreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("name",name);
        editor.putString("Phonekey",phoneno);
        editor.putString("Uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
        editor.apply();

    }

    public savetolocal(){
    }

    public String returnphoneno(Context context){
        return context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE).getString("Phonekey","null");
    }

    public String returnuid(Context context){
        return context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE).getString("Uid","null");
    }

    public String returnname(Context context){
        return context.getSharedPreferences(mypreference,Context.MODE_PRIVATE).getString("name","null");
    }

    public void setkey(Context context,String key){
        context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE).edit().putString("reckey",key);
    }

    public String getkey(Context context){
        return context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE).getString("reckey","null");
    }

    public void setkeyonecive(Context context,String key){
        context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE).edit().putString("sentkey",key);
    }

    public String getkeyonrecive(Context context){
        return context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE).getString("sentkey","null");
    }
}
