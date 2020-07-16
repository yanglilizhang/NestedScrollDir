package com.moj.nested.behavior;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * @Author yangsanning
 * @ClassName AvatarBehavior
 * @Description 头像随动
 * @Date 2020/4/27
 */
public class AvatarBehavior extends CoordinatorLayout.Behavior<ImageView> {

    /**
     * 处于中心时候原始X轴
     */
    private int originalX = 0;
    /**
     * 处于中心时候原始Y轴
     */
    private int originalY = 0;

    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency) {
        return dependency instanceof Toolbar;
    }

    //dependency 依赖对象
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull View dependency) {
        Log.e("**", "width=" + dependency.getWidth() + ",height=" + dependency.getHeight());
        // 计算X轴坐标
        if (originalX == 0) {
            this.originalX = dependency.getWidth() / 2 - child.getWidth() / 2;
        }
        // 计算Y轴坐标
        if (originalY == 0) {
//            originalY = dependency.getHeight() - child.getHeight();
            originalY = dependency.getHeight() - child.getHeight() / 2;
        }

        // X轴百分比
        float percentX = dependency.getY() / originalX;
        if (percentX >= 1) {
            percentX = 1;
        }

        // Y轴百分比
        float percentY = dependency.getY() / originalY;
        if (percentY >= 1) {
            percentY = 1;
        }

        float x = originalX - originalX * percentX;
        Log.e("**", "---------->x=" + x);
        if (x <= child.getWidth()) {
            x = child.getWidth();
        }

        // 头像的放大和缩小
        child.setScaleX(1 - percentY / 2);
        child.setScaleY(1 - percentY / 2);

        child.setX(x);
        child.setY(originalY - originalY * percentY);
        return true;
    }
}