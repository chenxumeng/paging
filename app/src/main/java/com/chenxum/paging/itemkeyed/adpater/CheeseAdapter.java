package com.chenxum.paging.itemkeyed.adpater;

import android.view.ViewGroup;


import com.chenxum.paging.db.Cheese;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

public class CheeseAdapter extends PagedListAdapter<Cheese,CheeseViewHolder> {
    private static DiffUtil.ItemCallback<Cheese> diffCallBack = new DiffUtil.ItemCallback<Cheese>() {
        @Override
        public boolean areItemsTheSame(@NonNull Cheese oldItem, @NonNull Cheese newItem) {

            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cheese oldItem, @NonNull Cheese newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };
    public CheeseAdapter() {
        super(diffCallBack);
    }

    @NonNull
    @Override
    public CheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheeseViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CheeseViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }
}
