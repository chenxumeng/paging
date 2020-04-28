package com.chenxum.paging;


import me.yokeyword.fragmentation.SupportActivity;

import android.os.Bundle;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadRootFragment(R.id.fl_container,ContainerFragment.newInstance());
    }
}
