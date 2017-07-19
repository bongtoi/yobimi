package com.example.nvquang.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.nvquang.adapter.RecyclerViewAdapter;
import com.example.nvquang.englishgrammertestdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by NVQuang on 07/07/2017.
 */

public class FragmentT extends FragmentBase implements RecyclerViewAdapter.CallBack {
    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private List<Integer> integerList = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private int n = 40;

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initDataDefault() {
        super.initDataDefault();

//        toolbar.inflateMenu(R.menu.main);
        toolbar.setTitle("Choose your test");

        integerList = loadData();

        recyclerViewAdapter = new RecyclerViewAdapter(integerList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        mainActivity.setUpWithToolbar(toolbar);
    }

    private List<Integer> loadData() {
        integerList = new ArrayList<>();
        for (int i = 1; i <= n ; i++) {
            integerList.add(i);
        }
        return integerList;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_t;
    }

    @Override
    public void OnClick(int index) {
        mainActivity.onOpenFragment(FragmentT2.newInstance(index), true);
    }

}
