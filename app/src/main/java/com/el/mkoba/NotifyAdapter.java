package com.el.mkoba;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class NotifyAdapter extends FirestoreRecyclerAdapter<Notification, NotifyAdapter.NotifyViewHolder> {


    private OnItemClickListener listener;
   private int hours;
   private int mins;
   private long diffDays;

   private long elapsedTimeMillis;
   private int daysdiff = 0;

    public NotifyAdapter(@NonNull FirestoreRecyclerOptions<Notification> options) {
        super(options);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull final NotifyViewHolder holder, int position, @NonNull final Notification model) {
        holder.Desc.setText(model.getBody());
        holder.Name.setText(model.getType()+"");
        long milisecond = model.getTimestamp().getTime();
        String date = DateFormat.format("MMMM/dd/yyyy hh:mm ",new Date(milisecond)).toString();


        final Date date1 = new Date();

        elapsedTimeMillis =  model.getTimestamp().getTime() - date1.getTime();

        mins = (int) ((elapsedTimeMillis/(1000*60)) % 60);

        if (mins <= 10)
        {
//            holder.dote.setVisibility(View.GONE);
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
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notify,
                parent, false);
        return new NotifyViewHolder(v);
    }

    class NotifyViewHolder extends RecyclerView.ViewHolder {

        public TextView Desc,Name,Date,dote;



        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);

            Desc = itemView.findViewById(R.id.Noti_desc);
            Name = itemView.findViewById(R.id.Noti_name);
            Date = itemView.findViewById(R.id.Noti_date);
            dote = itemView.findViewById(R.id.Noti_dot);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
