package com.chenxum.paging.itemkeyed.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenxum.paging.R;
import com.chenxum.paging.db.Cheese;


import androidx.recyclerview.widget.RecyclerView;

public class CheeseViewHolder extends RecyclerView.ViewHolder {
    private TextView textView = itemView.findViewById(R.id.textView);
    private Cheese cheese;

    public CheeseViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    public void bindTo(Cheese cheese){
        this.cheese = cheese;
        textView.setText(cheese.getName());
    }


}
