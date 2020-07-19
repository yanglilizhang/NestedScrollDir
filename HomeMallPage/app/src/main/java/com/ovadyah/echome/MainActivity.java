package com.ovadyah.echome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ovadyah.echome.demo1.ECHome1Activity;
import com.ovadyah.echome.demo2.ECHome2Activity;
import com.ovadyah.echome.demo3.ECHome3Activity;

/**
 * https://github.com/Ovadyah/HomeMallPage
 * https://www.jianshu.com/p/fee217ab4cd3
 * https://blog.csdn.net/u011387817/article/details/103317592?utm_medium=distribute.pc_relevant.none-task-blog-baidujs-8
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        this.initView();
    }

    private void initView() {
        findViewById(R.id.btn_ec_home1).setOnClickListener(this);
        findViewById(R.id.btn_ec_home2).setOnClickListener(this);
        findViewById(R.id.btn_ec_home3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case R.id.btn_ec_home1:
                ECHome1Activity.launch(this);
                break;
            case R.id.btn_ec_home2:
                ECHome2Activity.launch(this);
                break;
            case R.id.btn_ec_home3:
                ECHome3Activity.launch(this);
                break;
        }
    }
}
