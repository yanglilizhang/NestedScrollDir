package com.moj.nested.huawei;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.moj.nested.R;

/**
 * Ruomiz on 2018/6/14.
 * Time  waits  for  none
 * desc ： xxxx
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private SearchBar mSearch;
    private int i = 0;

    public static  void startToMe(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mSearch = findViewById(R.id.search);
        Button button = findViewById(R.id.bt);
        button.setOnClickListener(this);
        mSearch.setDuration(500)
                .setSearchIcon(R.drawable.search)
        .setSearchTextHint("最火爆的应用:搜索一下");

    }


    @Override
    public void onClick(View v) {
        if (i % 2 == 0) {
            mSearch.closeAnimation();
        } else {
            mSearch.startAnimation();
        }
        i++;
    }
}