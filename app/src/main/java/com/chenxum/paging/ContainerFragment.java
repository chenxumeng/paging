package com.chenxum.paging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenxum.paging.itemkeyed.ItemKeyedFragment;
import com.chenxum.paging.position.PositionFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class ContainerFragment extends SupportFragment {

    public static ContainerFragment newInstance() {

        Bundle args = new Bundle();

        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.btn_test1, R.id.btn_test2, R.id.btn_test3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test1:
                start(PositionFragment.newInstance());
                break;
            case R.id.btn_test2:
                start(ItemKeyedFragment.newInstance());
                break;
            case R.id.btn_test3:
                break;
        }
    }

}
