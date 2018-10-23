package com.encryptin.junaid.encryptionapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

   View mView;
    public ViewHolder(View itemView) {
        super(itemView);

        mView=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListner.onItemClick (view,getAdapterPosition ());
            }
        });

    }

    public void setDetails(contactlistActivity context, String name,String phoneno) {

        TextView tvname=mView.findViewById(R.id.namesinglelay);
        TextView tvprice=mView.findViewById(R.id.phonesinglelay);

        tvname.setText(name);
        tvprice.setText(phoneno);

    }

    public interface ClickListner{
        void onItemClick(View view,int position);
    }
    ViewHolder.ClickListner mClickListner;
    public void setOnClickListner(ViewHolder.ClickListner clickListner){
        mClickListner=clickListner;
    }


}
