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
 * Description:coordinatorLayout与AppBarLayout的使用
 */

public class CdlWithAppBarActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdl_with_appbar);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleStringAdapter(initStrings(), this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    /**
     * SCROLL_FLAG_NO_SCROLL	0x0
     * 设置这个flag，将表示该View不能被滑动。也就是说不参与联动。
     * <p>
     * SCROLL_FLAG_SCROLL	0x01
     * 设置这个Flag，表示该View参与联动。具体效果需要跟其他Flag组合。
     * <p>
     * SCROLL_FLAG_EXIT_UNTIL_COLLAPSED	0x02
     * 设置这个Flag，表示当View被推出屏幕时，会跟着滑动，直到折叠到View的最小高度；同时只有在其他View(比如说RecyclerView)滑动到顶部才会展开。
     * <p>
     * SCROLL_FLAG_ENTER_ALWAYS	0x02
     * 设置这个Flag，不管是View是滑出屏幕还是滑进屏幕，该View都能立即响应滑动事件，跟随滑动。比如说，如果该View是折叠的，当RecyclerView向下滑动时，该View随时都能跟随展开；反之亦然。
     * <p>
     * SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED	0x04
     * 在SCROLL_FLAG_ENTER_ALWAYS的基础上，该Flag增加了折叠到固定高度的限制。在View下拉过程中，首先会将该View显示minHeight的高度，RecyclerView在继续下拉（这里以RecyclerView为例）。注意，该Flag在SCROLL_FLAG_ENTER_ALWAYS前提下生效。
     * <p>
     * SCROLL_FLAG_SNAP	0x08
     * 该Flag表示View拥有吸附功能。比如说，当前滑动停止，View离底部更近，那么就会折叠；反之则会展开。
     */
    private List<String> initStrings() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("简单文本" + i);
        }
        return list;
    }
}
