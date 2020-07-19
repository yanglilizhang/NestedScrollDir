package com.app.behavior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.app.behavior.behavior.youxian.StatusBarUtil;

/**
 * https://github.com/EnumContent/MyCustomView
 */
public class YouXianActivity extends AppCompatActivity {

    View statusBarView;
    View statusBarViewHolder;
    NestedScrollView myScrollView;
    TextView clic;
    View statusBarViewHolder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        setContentView(R.layout.activity_youxian);
        statusBarView = findViewById(R.id.status_bar_view);
        statusBarViewHolder = findViewById(R.id.status_bar_view_holder);
        statusBarViewHolder2 = findViewById(R.id.status_bar_view_holder2);
        clic = findViewById(R.id.clic);
        statusBarView.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(this);
        statusBarViewHolder.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(this);
        statusBarViewHolder2.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(this);
        clic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onc(v);
            }
        });
    }

    public void onc(View view) {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
    }

}
