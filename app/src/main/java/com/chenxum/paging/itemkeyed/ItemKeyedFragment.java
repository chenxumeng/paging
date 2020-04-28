package com.chenxum.paging.itemkeyed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenxum.paging.R;
import com.chenxum.paging.itemkeyed.adpater.CheeseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * ItemKeyedDataSource 测试
 */
public class ItemKeyedFragment extends SupportFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static ItemKeyedFragment newInstance() {

        Bundle args = new Bundle();

        ItemKeyedFragment fragment = new ItemKeyedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_keyed, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    private final String TAG = "ItemKeyedFragment";
    private CheeseAdapter adapter = new CheeseAdapter();
    private CheeseViewModel viewModel = new CheeseViewModel(110);
    private void initView(){
        recyclerView.setAdapter(adapter);
        viewModel.getLivePagedList().observe(getViewLifecycleOwner(),cheeses -> {
            Log.d(TAG,"===submitList==="+cheeses.size());
            adapter.submitList(cheeses);
        });
    }
}
