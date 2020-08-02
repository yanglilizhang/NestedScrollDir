package com.example.doublesucktopdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.doublesucktopdemo.adapter.HomeViewPagerAdapter;
import com.example.doublesucktopdemo.adapter.MyBannerAdapter;
import com.example.doublesucktopdemo.adapter.RecommendAdapter;
import com.example.doublesucktopdemo.widget.RecyclerViewDivider;
import com.example.doublesucktopdemo.widget.RefreshHeaderView.TodayNewsHeader;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.appbar.AppBarLayout;
import com.leaf.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * https://github.com/weioule/DoubleSuckTopDemo
 * 仿京东首页双层吸顶效果
 */
public class MainActivity extends FragmentActivity {
    private SmartRefreshLayout refreshView;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mFloatSearchRl;
    private LinearLayout mHeaderLl;
    private SlidingTabLayout mTabView;
    private ViewPager mViewPager;

    private int totalScrollRange, oldVerticalOffset = -1;
    private HomeViewPagerAdapter fragmentAdapter;
    private String[] titles = {"首页", "手机", "食品", "生鲜", "休闲零食", "家居厨具", "美妆"};
    private ArrayList<ProductBean> list = new ArrayList<>();
    private List<Fragment> fragmentList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }

    private void initView() {
        StatusBarUtil.setColor(this, getColor(R.color.theme_color));
        refreshView = findViewById(R.id.refresh_view);
        mFloatSearchRl = findViewById(R.id.rl_float_search);
        mAppBarLayout = findViewById(R.id.app_bar);
        mTabView = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mHeaderLl = findViewById(R.id.ll_header);
        refreshView.setRefreshHeader(new TodayNewsHeader(this));
        addProductBeans();
        getData();
    }

    private void addProductBeans() {
        for (int i = 0; i < 10; i++) {
            ProductBean bean = new ProductBean();
            switch (i) {
                case 0:
                case 9:
                    bean.setName("鲜肉粽");
                    bean.setImageResource(R.drawable.img01);
                    bean.setContent("当吃到蛋黄与鲜肉后，才知咸香入口的双重体验，能叫人如此回味");
                    break;
                case 1:
                case 8:
                    bean.setName("豆沙粽");
                    bean.setImageResource(R.drawable.img02);
                    bean.setContent("绵软细腻的豆沙，融化在温热糯米中，此时的豆沙馅儿入口即化，甜而不腻");
                    break;
                case 2:
                case 7:
                    bean.setName("蜜枣粽");
                    bean.setImageResource(R.drawable.img03);
                    bean.setContent("细细品味，蜜枣中渗出的糖蜜，浸润了周围的糯米，清甜绵密，糯而爽口");
                    break;
                case 3:
                case 6:
                    bean.setName("栗蓉粽");
                    bean.setImageResource(R.drawable.img04);
                    bean.setContent("清甜口味，料多馅足，满足你的挑剔味蕾");
                    break;
                case 4:
                case 5:
                    bean.setName("咸鸭蛋");
                    bean.setImageResource(R.drawable.img05);
                    bean.setContent("松沙酥软，入口绵密的咸鸭蛋，香醇浓郁的口感，让人不禁留恋忘返，回味不止");
                    break;
            }

            bean.setPrice("¥49.50");
            list.add(bean);
        }
    }

    private void addListener() {
        mAppBarLayout.addOnOffsetChangedListener(offsetChangedListener);
        refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    private void loadData() {
        refreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshView.finishRefresh();
                refreshView.setEnabled(true);
            }
        }, 800);
    }

    // 这里的处理是当AppBarLayout处于最顶部也就是完全打开状态verticalOffse=0时才允许刷新控件可用.
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffse) {
            totalScrollRange = appBarLayout.getTotalScrollRange();

            if (oldVerticalOffset == verticalOffse) return;

            if (verticalOffse == 0) {
                //AppBarLayout无任何滑动，展开状态
                refreshView.setEnabled(true);
            } else {
                refreshView.setEnabled(false);
            }

            if (verticalOffse <= -Utils.dp2px(40)) {
                mFloatSearchRl.setVisibility(View.VISIBLE);
            } else {
                mFloatSearchRl.setVisibility(View.GONE);
            }

            if (Math.abs(verticalOffse) >= totalScrollRange && totalScrollRange != 0) {
                mTabView.setBackgroundColor(getResources().getColor(R.color.white_color));
            } else {
                mTabView.setBackgroundColor(getResources().getColor(R.color.background_color));
            }

            oldVerticalOffset = verticalOffse;
        }
    };

    public void getData() {
        mHeaderLl.removeAllViews();

        addTopView();
        addBannerView();
        addRecommendView();
        addFeaturedView();
        initViewpager();
    }

    private void addTopView() {
        View mTopView = getLayoutInflater().inflate(R.layout.top_view_layout, mHeaderLl, false);
        mHeaderLl.addView(mTopView);
    }

    private void addBannerView() {
        View mBannerView = getLayoutInflater().inflate(R.layout.banner_layout, mHeaderLl, false);
        Banner mBanner = mBannerView.findViewById(R.id.banner);

        ArrayList<ProductBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProductBean bean = new ProductBean();
            bean.setName("banner " + i);
            switch (i) {
                case 0:
                    bean.setImageResource(R.drawable.img1);
                    break;
                case 1:
                    bean.setImageResource(R.drawable.img2);
                    break;
                case 2:
                    bean.setImageResource(R.drawable.img3);
                    break;
            }

            list.add(bean);
        }

        mBanner.addBannerLifecycleObserver(this)
                .setAdapter(new MyBannerAdapter(list))
                .setIndicator(new CircleIndicator(this))
                .start();

        mHeaderLl.addView(mBannerView);
    }

    private void addRecommendView() {
        RecyclerView mRecyclerView = new RecyclerView(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        RecommendAdapter adapter = new RecommendAdapter(list);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ProductBean productBean = (ProductBean) adapter.getItem(position);
                Toast.makeText(MainActivity.this, productBean.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(adapter);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setStyle(RecyclerViewDivider.Style.BOTH)
                .setColor(0x00000000)
                .setOrientation(RecyclerViewDivider.GRIDE_VIW)
                .setSize(5)
                .build();
        mRecyclerView.addItemDecoration(divider);

        mHeaderLl.addView(mRecyclerView);
    }

    private void addFeaturedView() {
        View view = getLayoutInflater().inflate(R.layout.featured_layout, mHeaderLl, false);
        mHeaderLl.addView(view);
    }

    private void initViewpager() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        } else {
            fragmentList.clear();
        }

        for (int i = 0; i < titles.length; i++) {
            fragmentList.add(new HomeFragment(list));
        }

        fragmentAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(fragmentAdapter);
        mTabView.setOnTabSelectListener(onTabSelectListener);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

        mTabView.setViewPager(mViewPager, titles);
    }

    private OnTabSelectListener onTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            mTabView.setCurrentTab(position);
        }

        @Override
        public void onTabReselect(int position) {
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mTabView.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 控制appbar的滑动
     *
     * @param isScroll true 允许滑动   false 禁止滑动
     */
    private void banAppBarScroll(boolean isScroll) {
        View mAppBarChildAt = mAppBarLayout.getChildAt(0);
        AppBarLayout.LayoutParams mAppBarParams = (AppBarLayout.LayoutParams) mAppBarChildAt.getLayoutParams();
        if (isScroll && mAppBarParams.getScrollFlags() == 0) {
            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            mAppBarChildAt.setLayoutParams(mAppBarParams);
        } else if (mAppBarParams.getScrollFlags() != 0)
            mAppBarParams.setScrollFlags(0);
    }

    /**
     * 控制appbar的开关
     * 需要手动开关时调用
     *
     * @param open true 打开   false 关闭
     */
    public void switchAppbarLayout(boolean open) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (open && topAndBottomOffset != 0) {
                //打开
                appBarLayoutBehavior.setTopAndBottomOffset(0);
            } else if (!open) {
                //关闭
                if (totalScrollRange == 0) totalScrollRange = mAppBarLayout.getHeight();
                appBarLayoutBehavior.setTopAndBottomOffset(-totalScrollRange);
            }
        }
    }



}