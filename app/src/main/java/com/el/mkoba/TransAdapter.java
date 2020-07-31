package com.el.mkoba;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class TransAdapter extends FirestoreRecyclerAdapter<Mkoba_Transactions, TransAdapter.TransViewHolder> {


    private OnItemClickListener listener;
   private int hours;
   private int mins;
   private long diffDays;

   private long elapsedTimeMillis;
   private int daysdiff = 0;

    public TransAdapter(@NonNull FirestoreRecyclerOptions<Mkoba_Transactions> options) {
        super(options);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull final TransViewHolder holder, int position, @NonNull final Mkoba_Transactions model) {
        holder.Desc.setText(model.getDescription());
        holder.Rcpt_NO.setText(""+model.getRecipient_No());
        holder.Amount.setText("Ksh "+model.getAmount()+"");
        holder.sts_percent.setText(model.getStatus_percent()+"%");
        holder.pay_id.setText(model.getPayment_Id());
        holder.title.setText(model.getPayment_Title());
        long milisecond = model.getTimestamp().getTime();
        String date = DateFormat.format("MMMM/dd/yyyy hh:mm ",new Date(milisecond)).toString();

        if (model.getStatus_percent()== 100){

            holder.delete.setVisibility(View.VISIBLE);
            holder.Amount.setTextColor(R.color.colorGrey);
        }


         //elapsedTimeMillis =  model.getTimestamp().getTime() - date.getTime();


//         diffDays = elapsedTimeMillis / (24 * 60 * 60 * 1000) + 1;
//         daysdiff = (int) diffDays;

         holder.Date.setText(date);





    }


    ///Delete item
    public void deleteItem (int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }


    @NonNull
    @Override
    public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trans,
                parent, false);
        return new TransViewHolder(v);
    }

    class TransViewHolder extends RecyclerView.ViewHolder {

        public TextView Desc,Rcpt_NO,DeadLine,Amount,Date,title, pay_id,sts_percent,delete;


        public TransViewHolder(@NonNull View itemView) {
            super(itemView);

            Desc = itemView.findViewById(R.id.V_desc);
            Rcpt_NO = itemView.findViewById(R.id.V_rcptNo);
            DeadLine = itemView.findViewById(R.id.V_deadline);
            Amount = itemView.findViewById(R.id.V_amount);
            Date = itemView.findViewById(R.id.V_date);
            pay_id = itemView.findViewById(R.id.V_pay_Id);
            sts_percent = itemView.findViewById(R.id.V_status_percent);
            title = itemView.findViewById(R.id.V_title);
            delete = itemView.findViewById(R.id.V_delete);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });



        }
    }


    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
