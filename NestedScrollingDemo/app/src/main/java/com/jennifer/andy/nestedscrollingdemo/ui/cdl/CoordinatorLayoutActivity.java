package com.jennifer.andy.nestedscrollingdemo.ui.cdl;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jennifer.andy.nestedscrollingdemo.R;

/**
 * Author:  andy.xwt
 * Date:    2018/8/8 13:56
 * Description:CoordinatorLayout的效果展示。
 * https://juejin.im/post/5d430c5a6fb9a06aeb109d56#heading-1
 */

public class CoordinatorLayoutActivity extends AppCompatActivity implements View.OnClickListener {

//    处理子控件的依赖下的交互
//    处理子控件的嵌套滑动
//    处理子控件的测量与布局
//    处理子控件的事件拦截与响应。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coord_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_demo1).setOnClickListener(this);
        findViewById(R.id.btn_demo2).setOnClickListener(this);
        findViewById(R.id.btn_demo3).setOnClickListener(this);
        findViewById(R.id.btn_demo4).setOnClickListener(this);
        findViewById(R.id.btn_demo5).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_demo1://多个view的协同交互效果
                startActivity(new Intent(this, CoordinatorLayoutDemo1Activity.class));
                break;
            case R.id.btn_demo2://不重写layoutDependsOn方法，而是在布局使用xml中使用layout_anchor来确定依赖关系
                startActivity(new Intent(this, CoordinatorLayoutDemo2Activity.class));
                break;
            case R.id.btn_demo3://自定义Behavior测量与布局
                startActivity(new Intent(this, CoordinatorLayoutDemo3Activity.class));
                break;
            case R.id.btn_demo4://Behavior嵌套滑动交互效果
                startActivity(new Intent(this, CoordinatorLayoutDemo4Activity.class));
                break;
            case R.id.btn_demo5://自定义Behavior事件拦截与处理
                startActivity(new Intent(this, CoordinatorLayoutDemo2Activity.class));
                break;
        }
    }
}
