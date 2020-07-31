package com.el.mkoba;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public  class CategoryViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public TextView Name;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        Name = itemView.findViewById(R.id.Category_name);

    }

    private CategoryViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }

    public void setOnClickListener(CategoryViewHolder.ClickListener  clickListener){

        mClickListener = clickListener;

    }


}
