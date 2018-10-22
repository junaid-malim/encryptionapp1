package com.encryptin.junaid.encryptionapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseBackgroundService extends Service {

    DatabaseReference retrivrkey=FirebaseDatabase.getInstance().getReference("keyslist").child(new savetolocal().returnphoneno());
//TODO build notification correctly

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();

        retrivrkey.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notifyer(getApplicationContext());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void notifyer(Context context){

       /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, Context.NOTIFICATION_SERVICE)
                .setSmallIcon(R.drawable.notiki)
                .setContentTitle("Encryption-App")
                .setContentText("YOU HAVE A NEW KEY.CLICK AND ADD FILE TO DECRYPT")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        */

            if (Build.VERSION.SDK_INT < 26) {
                return;
            }
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("OnNewKeyReived",
                    "on New key recived",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("new key channel");
            notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "OnNewKeyReived")
                .setContentTitle("Encryption App");

    }

}
