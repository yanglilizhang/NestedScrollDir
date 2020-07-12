package com.jennifer.andy.nestedscrollingdemo.ui.cdl;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jennifer.andy.nestedscrollingdemo.R;

/**
 * Author:  andy.xwt
 * Date:    2018/8/8 13:56
 * Description:
 * 没有实现NestedScrollingChild接口下，多个view的交互效果
 */

public class CoordinatorLayoutDemo1Activity extends AppCompatActivity {

    /**
     * CoordinatorLayout：协调者布局主要协调child之间的联动
     * 比如说，我们在滑动一个RecyclerView,存在一个View需要在
     * RecyclerView滑动时做相应的动作，例如，位移变化，缩放变化等等。
     */
//在CoordinatorLayout内部，每个child都必须带一个Behavior（其实不携带也行，不携带就不能被协调），
// CoordinatorLayout就根据每个child所携带的Behavior信息进行协调。

//Behavior不仅仅协助联动，而且还是接管了child的三大流程，有点类似于RecyclerView的LayoutManager。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdl_demo1);
    }
}
