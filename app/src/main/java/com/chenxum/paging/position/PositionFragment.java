package com.chenxum.paging.position;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenxum.paging.R;
import com.chenxum.paging.position.adapter.CheeseAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class PositionFragment extends SupportFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    public static PositionFragment newInstance() {

        Bundle args = new Bundle();

        PositionFragment fragment = new PositionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private final String TAG = "PositionFragment";
    private CheeseAdapter adapter = new CheeseAdapter();
    private CheeseViewModel viewModel = new CheeseViewModel(100);
    private void initView(){
        recyclerView.setAdapter(adapter);
        viewModel.getLivePagedList().observe(getViewLifecycleOwner(),cheeses -> {
            Log.d(TAG,"===submitList==="+cheeses.size());
            adapter.submitList(cheeses);
        });
    }
}
