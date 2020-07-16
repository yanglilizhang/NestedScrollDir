package com.jennifer.andy.nestedscrollingdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jennifer.andy.nestedscrollingdemo.ui.abl.CdlWithAppBarActivity;
import com.jennifer.andy.nestedscrollingdemo.ui.abl.CdlWithAppBarWithCollActivity;
import com.jennifer.andy.nestedscrollingdemo.ui.cdl.CoordinatorLayoutActivity;
import com.jennifer.andy.nestedscrollingdemo.ui.nested.NestedScrolling2DemoActivity;
import com.jennifer.andy.nestedscrollingdemo.ui.nested.NestedScrollingParent2Activity;
import com.jennifer.andy.nestedscrollingdemo.ui.nested.NestedScrollingParentActivity;
import com.jennifer.andy.nestedscrollingdemo.ui.nested.NestedTraditionActivity;

/**
 * https://github.com/AndyJennifer/NestedScrollingDemo
 *
 * https://github.com/Ruomiz/BehaviorSample
 * https://github.com/msdx/behavior-master
 * https://github.com/pengguanming/MiMusicBehavior
 * https://github.com/xukefeng/custom_behavior
 * https://github.com/EnumContent/MyCustomView
 * https://github.com/xuweitalang/WX_RecyclerView
 * https://github.com/879058443/NestedScrollRecyclerView
 * https://github.com/dxh104/MaterialDisgnPullApp
 * https://github.com/shengweiling/SlidingLayout
 * https://github.com/liuguangsen/HightUi
 * https://github.com/Ovadyah/HomeMallPage 2020仿照最新淘宝首页，京东首页，苏宁首页框架，自定义控件，解决连续滚动等多嵌套问题。
 * https://github.com/weioule/DoubleSuckTopDemo
 * https://github.com/xmuSistone/PersistentRecyclerView
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//            * https://github.com/sangxiaonian/EasyRefrush
//            * https://github.com/niuyouquan/HomeDemo
//            * https://github.com/BmobSnail/SlideNestedPanelLayout
//            * https://github.com/hufeiyang/NestedScrollingParent2Layout
//            * https://github.com/taolin2107/SlideConflict
//            * https://github.com/JohLiu/NestedConflict
//            * https://github.com/Clearlove2015/UseFixedHeaderScrollView
//            * https://github.com/flowerthorn/FixedTopBarDemo
//            * https://github.com/ml1953/ReboundScrollView
//            * https://github.com/chidehang/ComboScrollLayout
//            * https://github.com/caocao123/ImitationJD
//            *
//            * https://github.com/Jeromeer/HorizontalScrollDemo-master


    //https://github.com/yangsanning/BaseItemDecoration
    //RecyclerView 的分割线封装，便捷处理各种边界
    //https://github.com/yangsanning/CustomTextView
    //https://github.com/yangsanning/BasePaint
    //https://github.com/yangsanning/MagicalTextView
    //https://github.com/yangsanning/EasyRefreshLayout
    //https://github.com/yangsanning/CropImageView
    //https://github.com/yangsanning/CheckView
//https://github.com/yangsanning/WanAndroid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewAndSetListener();
    }

    private void findViewAndSetListener() {
        findViewById(R.id.btn_nested_scrolling_tradition).setOnClickListener(this);
        findViewById(R.id.btn_nested_scrolling).setOnClickListener(this);
        findViewById(R.id.btn_nested_scrolling2).setOnClickListener(this);
        findViewById(R.id.btn_nested_scrolling2Demo).setOnClickListener(this);
        findViewById(R.id.btn_coordinator_layout).setOnClickListener(this);
        findViewById(R.id.btn_coor_with_appbar).setOnClickListener(this);
        findViewById(R.id.btn_coor_with_appbar_with_coll).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_nested_scrolling_tradition://传统嵌套滑动
                startActivity(new Intent(this, NestedTraditionActivity.class));
                break;
            case R.id.btn_nested_scrolling://实现NestedScrollingParent机制的嵌套滑动
                startActivity(new Intent(this, NestedScrollingParentActivity.class));
                break;
            case R.id.btn_nested_scrolling2://实现NestedScrollingParent2机制的嵌套滑动
                startActivity(new Intent(this, NestedScrollingParent2Activity.class));
                break;
            case R.id.btn_nested_scrolling2Demo://嵌套滑动实际使用例子
                startActivity(new Intent(this, NestedScrolling2DemoActivity.class));
                break;
            case R.id.btn_coordinator_layout://CoordinatorLayout效果展示
                startActivity(new Intent(this, CoordinatorLayoutActivity.class));
                break;
            case R.id.btn_coor_with_appbar://CoordinatorLayout与AppBarLayout结合使用
                startActivity(new Intent(this, CdlWithAppBarActivity.class));
                break;
            case R.id.btn_coor_with_appbar_with_coll://CoordinatorLayout与AppBarLayout与CollapsingToolbarLayout 三者结合使用
                startActivity(new Intent(this, CdlWithAppBarWithCollActivity.class));
                break;
        }
    }
}
