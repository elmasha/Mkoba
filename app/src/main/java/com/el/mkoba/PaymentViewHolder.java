package com.el.mkoba;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public  class PaymentViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public TextView pay_method,pay_date,Amount,pay_desc;

    public PaymentViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        pay_method = itemView.findViewById(R.id.Pay_method);
        pay_date = itemView.findViewById(R.id.Pay_date);
        pay_desc  = itemView.findViewById(R.id.Pay_desc);
        Amount = itemView.findViewById(R.id.Pay_amount);


//        itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                mClickListener.onItemLongClick(v,getAdapterPosition());
//                return false;
//
//
//            }
//        });

    }

    private PaymentViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }

    public void setOnClickListener(PaymentViewHolder.ClickListener  clickListener){

        mClickListener = clickListener;

    }


}
