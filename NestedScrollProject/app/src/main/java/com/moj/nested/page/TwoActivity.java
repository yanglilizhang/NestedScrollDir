package com.moj.nested.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;


import com.moj.nested.R;


/**
 * @Author https://github.com/yangsanning/BehaviorDemo
 * @ClassName https://github.com/yangsanning/TableView
 * @Description 一句话概括作用
 * @Date 2020/4/29
 */
public class TwoActivity extends AppCompatActivity implements View.OnClickListener {
    public static  void startToMe(Context context) {
        Intent intent = new Intent(context, TwoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        LinearLayout rootView = findViewById(R.id.main_activity_root);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            rootView.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_activity_avatar_behavior:
                startActivity(AvatarActivity.class);
                break;
            case R.id.main_activity_follow_up_behavior:
                startActivity(FollowUpActivity.class);
                break;
            default:
                break;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}