package com.app.behavior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.behavior.behavior.test.FeedAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * https://raw.githubusercontent.com/xuweitalang/WX_RecyclerView
 */
public class WxActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;
    private int barHeight; //悬浮条的高度
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WxActivity.this, ImmerseActivity.class));
            }
        });

        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_nickname);
        mSuspensionIv = findViewById(R.id.iv_avatar);

        recyclerView = findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FeedAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取悬浮条的高度
                barHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //监听滚动，对悬浮条的位置进行调整

                //方法：判断下一个item的顶部距离recycleview的顶部的距离是否小于悬浮条的高度，则需要移动悬浮条
                //如果大于悬浮条的高度，则保证悬浮条在默认位置即可

                //步骤1：先找到下一个item
                View viewByNextPosition = layoutManager.findViewByPosition(mCurrentPosition + 1);
                if (viewByNextPosition != null) {
                    if (viewByNextPosition.getTop() <= barHeight) {
                        //需要移动悬浮条:因为是向上移动，所有要加负号
                        mSuspensionBar.setY(-(barHeight - viewByNextPosition.getTop()));
                    } else {
                        //保持在原来的位置
                        mSuspensionBar.setY(0);
                    }
                }

                //当前的位置不是第一个可见的位置
                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                    //跳转当前位置
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
                    //更新悬浮条
                    updateSuspensionBar();
                }

            }
        });

        updateSuspensionBar();
    }

    private void updateSuspensionBar() {
//        Picasso.with(this)
//                .load(getAvatarResId(mCurrentPosition))
//                .centerInside()
//                .fit()
//                .into(mSuspensionIv);
        mSuspensionTv.setText("NetEase " + mCurrentPosition);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }


}
