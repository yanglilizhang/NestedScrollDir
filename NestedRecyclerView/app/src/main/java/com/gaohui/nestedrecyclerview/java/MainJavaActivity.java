package com.gaohui.nestedrecyclerview.java;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.gaohui.nestedrecyclerview.BaseMenuActivity;
import com.gaohui.nestedrecyclerview.R;
import com.gaohui.nestedrecyclerview.java.adapter.MultiTypeAdapter;
import com.gaohui.nestedrecyclerview.java.bean.CategoryBean;
import com.gaohui.nestedrecyclerview.java.view.ParentRecyclerView;
import com.gaohui.nestedrecyclerview.java.view.StoreSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * https://github.com/JasonGaoH/NestedRecyclerView
 * RecyclerView实现tab的吸顶效果
 * https://juejin.im/post/6844903922482561037#comment
 */
public class MainJavaActivity extends BaseMenuActivity {
//1、Fling和Scroll，是否滑动到顶部，canScrollVertically函数，目前上层参数对象固定为RecyclerView，
// 可以将RecylerView 换成View，用来兼容子 Fragment 使用WebView 等非RecyclerView 的情况。

// 2、在某些场景中，是否滑动到顶部，仅依靠：canScrollVertically，是不够严谨的，
//  还需要结合其他方式来增加判定。例如：借助 第一个完全显示的元素 等

//3、ViewPage 上下滑动时，Footer ViewHolder 会存在 AttachedToWindow 和DetachedToWindow 的情况，
// 资源分配&释放，会出现子类目页卡顿。尤其是一些比较吃内存的子类目页，也会有内存抖动的情况。
    ArrayList<Object> mDataList = new ArrayList<Object>();

    MultiTypeAdapter adapter = new MultiTypeAdapter(mDataList);

    StoreSwipeRefreshLayout storeSwipeRefreshLayout;
    ParentRecyclerView javaRecyclerView;

    Long lastBackPressedTime = 0L;

    String[] strArray = new String[]{"推荐", "视频", "直播", "图片", "精华", "热门"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        javaRecyclerView = findViewById(R.id.javaRecyclerView);

        storeSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        javaRecyclerView.setAdapter(adapter);

        javaRecyclerView.initLayoutManager(this);

        refresh();

        storeSwipeRefreshLayout.setColorSchemeColors(Color.RED);
        storeSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
            super.onBackPressed();
        } else {
            javaRecyclerView.scrollToPosition(0);
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastBackPressedTime = System.currentTimeMillis();
        }
    }

    private void refresh() {
        mDataList.clear();
        for (int i = 0; i < 8; i++) {
            mDataList.add("parent item text " + i);
        }
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.getTabTitleList().clear();
        categoryBean.getTabTitleList().addAll(Arrays.asList(strArray));
        mDataList.add(categoryBean);
        adapter.notifyDataSetChanged();
        storeSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.destroy();
    }
}
