package com.jennifer.andy.nestedscrollingdemo.ui.abl;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.andy.nestedscrollingdemo.R;
import com.jennifer.andy.nestedscrollingdemo.adapter.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  andy.xwt
 * Date:    2018/8/8 13:56
 * Description:
 * CollapsingToolbarLayout主要是实现折叠布局的
 * CollapsingToolbarLayout需要作为AppBarLayout子View
 */
public class CdlWithAppBarWithCollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdl_abl_ctl);
        initView();
    }

    private void initView() {
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SimpleStringAdapter(initStrings(), this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

//    COLLAPSE_MODE_OFF	0	默认值，表示View不会有任何属性
//    COLLAPSE_MODE_PIN	1	当CollapsingToolbarLayout完全收缩之后，设置该Flag的View会保留在屏幕当中。
//    COLLAPSE_MODE_PARALLAX	2	设置该Flag的View会跟内容滚动，可以通过setParallaxMultiplier方法来设置视图差比率，其中0表示毫无视图差，完全跟内容滚动同步；1表示View完全不动。默认的视图差为0.5。

    private List<String> initStrings() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "--->CollapsingToolbarLayout与AppBarLayout");
        }
        return list;
    }
}
