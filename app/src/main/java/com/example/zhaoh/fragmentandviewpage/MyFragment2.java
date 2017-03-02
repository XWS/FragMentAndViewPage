package com.example.zhaoh.fragmentandviewpage;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoh.fragmentandviewpage.databinding.Layout2Binding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoh on 2016/5/24.
 */
public class MyFragment2 extends Fragment {

    private Layout2Binding binding;
    private List<String> list;
    private CommonAdapter<String> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout2, container, false);
        list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            list.add(Integer.toString(i));
        }
        init();
        RxBus.toObservable()
                .filter(String.class::isInstance)
                .cast(String.class)
                .subscribe(binding.tvClick::setText);
        return binding.getRoot();

    }

    public void init() {
        mAdapter = new CommonAdapter<>(R.layout.recycleritem, list);
        mAdapter.reset();
        mAdapter.setLoadMoreAction(binding.recyclerView, () -> {
            for (int i = 16; i < 32; i++) {
                list.add(Integer.toString(i));
                mAdapter.reset();
                mAdapter.notifyDataSetChanged();
                mAdapter.finish();
            }
        });
        binding.recyclerView.setAdapter(mAdapter);
    }
}
