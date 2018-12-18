package com.dysania.hencoderplus06.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dysania.hencoderplus06.R;
import com.dysania.hencoderplus06.model.PageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dysania on 12/12/18
 */

public class MainActivity extends AppCompatActivity {
    private List<PageModel> mPageModels = new ArrayList<>();

    {
        mPageModels.add(new PageModel(R.string.title_dashboard_view, R.layout.view_dashboard));
        mPageModels.add(new PageModel(R.string.title_pie_chart_view, R.layout.view_piechart));
        mPageModels.add(new PageModel(R.string.title_avatar_view, R.layout.view_avatar));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(mPageModels.get(position).getLayoutRes());
            }

            @Override
            public int getCount() {
                return mPageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return getString(mPageModels.get(position).getTitleRes());
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }
}
