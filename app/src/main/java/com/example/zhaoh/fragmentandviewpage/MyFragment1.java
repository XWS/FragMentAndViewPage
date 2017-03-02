package com.example.zhaoh.fragmentandviewpage;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoh.fragmentandviewpage.databinding.Layout1Binding;

import org.threeten.bp.LocalDate;

/**
 * Created by zhaoh on 2016/5/24.
 */
public class MyFragment1 extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Layout1Binding binding = DataBindingUtil.inflate(inflater, R.layout.layout1, container, false);
        Log.e("MyFragment1", "onCreateView");

        LocalDate now = LocalDate.now();
        now.getYear();
        now.getDayOfMonth();

        binding.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        RxBus.send("已点击");
    }
}
