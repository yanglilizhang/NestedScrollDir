package com.moj.nested.StickyLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.moj.nested.R;
import com.moj.nested.huawei.MarketActivity;

/**
 * https://github.com/Administrator0-0/BehaviorDemo
 */
public class StickyActivity extends AppCompatActivity {

    private TabLayout toolbar_tab;
    private ViewPager main_vp_container;

    public static void startToMe(Context context) {
        Intent intent = new Intent(context, StickyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        toolbar_tab = findViewById(R.id.toolbar_tab);
        main_vp_container = findViewById(R.id.main_vp_container);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        main_vp_container.setAdapter(vpAdapter);
        main_vp_container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbar_tab));
        toolbar_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(main_vp_container));
    }
}
