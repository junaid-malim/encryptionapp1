package com.encryptin.junaid.encryptionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class contactlistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        recyclerView = findViewById(R.id.contactlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(contactlistActivity.this));
        recyclerView.setItemViewCacheSize(20);
    }

    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Models, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Models, ViewHolder>(Models.class, R.layout.singlecontactlayout, ViewHolder.class, reference) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Models model, int position) {
                viewHolder.setDetails(contactlistActivity.this, model.getUsname(), model.getPhno());

            }
            public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

                final ViewHolder viewHolder=super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
                return viewHolder;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
