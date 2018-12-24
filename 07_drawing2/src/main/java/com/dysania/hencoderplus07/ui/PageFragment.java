package com.dysania.hencoderplus07.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dysania on 12/12/18
 */

public class PageFragment extends Fragment {
    @LayoutRes
    private int mLayoutRes;

    public static PageFragment newInstance(@LayoutRes int layoutRes) {
        Bundle args = new Bundle();
        args.putInt("layoutRes", layoutRes);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mLayoutRes = args.getInt("layoutRes");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutRes, container, false);
        return view;
    }
}
