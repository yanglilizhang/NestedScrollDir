package com.app.behavior;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

/**
 * https://raw.githubusercontent.com/xuweitalang/WX_RecyclerView
 * android 沉浸式状态栏实现
 */
public class ImmerseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immerse);
        setHeightAndPadding(this, findViewById(R.id.toolBar));

        //以下适用于android5.0以下版本
        //将内容填充到虚拟导航栏（设备有虚拟导航栏才有效）
        //等同于在style中设置： <item name="android:windowTranslucentNavigation">true</item>
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //将内容填充到状态栏
        //等同于在style中设置：<item name="android:windowTranslucentStatus">true</item>
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        immersive();
    }

    //设置代码沉浸式状态栏
    private void immersive() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { //android4.4以下不支持沉浸式
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //android5.0以上
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //先清除透明状态栏的标识
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); //添加一个绘制状态栏的标识
            window.setStatusBarColor(Color.TRANSPARENT); //设置状态栏颜色为透明

            int uiVisibility = window.getDecorView().getSystemUiVisibility();
            uiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; //追加布局内容全屏展示的属性
//            uiVisibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //隐藏虚拟导航栏
            uiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE; //防止内容区域大小发生变化
            window.getDecorView().setSystemUiVisibility(uiVisibility);
        } else { //android 5.0之前的设置
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//隐藏虚拟导航栏
        }
    }

    //获取状态栏高度
    public int getStatusBarHeight(Context context) {
        //获取状态栏的资源id，固定写法
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }

    public void setHeightAndPadding(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height += getStatusBarHeight(context); //将View的高度增加状态栏高度
        //设置paddingTop值增加状态栏高度
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                view.getPaddingRight(), view.getPaddingBottom());
    }
}