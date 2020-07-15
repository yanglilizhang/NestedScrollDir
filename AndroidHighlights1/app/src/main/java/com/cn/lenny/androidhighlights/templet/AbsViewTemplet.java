package com.cn.lenny.androidhighlights.templet;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-12
 */
public abstract class AbsViewTemplet implements IBaseConstant, IViewTemplet, ICallBackForListview, View.OnClickListener {
    protected AbsViewTemplet mTemplet = this;
    protected Context mContext;
    protected View mLayoutView;
    protected ViewGroup parent;
    protected int viewType;
    protected int position;
    protected Object rowData;
    protected int mScreenWidth = 1080;
    protected int mScreenHeight = 1920;
    protected ITempletBridge mUIBridge;
    protected float mDensity = 3.0F;
    protected boolean isVisibleToUser = true;
    protected final String TAG = this.getClass().getSimpleName();
    protected static boolean DEBUG = false;

    public AbsViewTemplet(Context mContext) {
        this.mContext = mContext;
        this.mDensity = mContext.getResources().getDisplayMetrics().density;
        this.mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        this.mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onClick(View v) {
        try {
            this.itemClick(v, this.position, this.rowData);
        } catch (Exception var3) {
            var3.printStackTrace();
            Log.d(this.TAG, "点击跳转发生异常，原因：" + var3.getMessage());
        }

    }

    @Override
    public View inflate(int viewType, int position, ViewGroup parent) {
        this.parent = parent;
        this.viewType = viewType;
        this.position = position;
        this.mLayoutView = this.bindView();
        if (null == this.mLayoutView) {
            int layout = this.bindLayout();
            if (null != parent) {
                this.mLayoutView = LayoutInflater.from(this.mContext).inflate(layout, parent, false);
            } else {
                this.mLayoutView = LayoutInflater.from(this.mContext).inflate(layout, parent);
            }
        }

        if (null != this.mLayoutView && !(this.mLayoutView instanceof AdapterView)) {
            this.mLayoutView.setOnClickListener(this);
        }

        return this.mLayoutView;
    }

    @Override
    public View inflate(int viewType, int position, Object rowData, ViewGroup parent) {
        this.rowData = rowData;
        return this.inflate(viewType, position, parent);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void holdCurrentParams(int viewType, int position, Object rowData) {
        this.viewType = viewType;
        this.position = position;
        this.rowData = rowData;
    }

    public void setVisibleToUser(boolean visible) {
        this.isVisibleToUser = visible;
    }

    @Override
    public void setUIBridge(ITempletBridge mUIBridge) {
        this.mUIBridge = mUIBridge;
    }

    @Override
    public View getItemLayoutView() {
        return this.mLayoutView;
    }

    public int getViewType() {
        return this.viewType;
    }

    public void itemClick(View view, int position, Object rowData) {
        Log.d(this.TAG, "view-->" + view + " position=" + position + " rowData=" + rowData);

    }

    protected void forward(View view, int position, Object rowData) {
    }

    protected int getPxValueOfDp(float dpValue) {
        return (int)(this.mDensity * dpValue + 0.5F);
    }

    protected View findViewById(int id) {
        return null != this.mLayoutView ? this.mLayoutView.findViewById(id) : null;
    }


    public int getColor(String strColor, String defaultColor) {
        String color = this.isColor(strColor) ? strColor.trim() : defaultColor;
        return Color.parseColor(color);
    }

    public boolean isColor(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            str = str.trim();
            if (!str.startsWith("#") || str.length() != 7 && str.length() != 9) {
                return false;
            } else {
                String pattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$";
                return Pattern.matches(pattern, str);
            }
        }
    }

    public static <D extends IViewTemplet> D createViewTemplet(Class<D> mViewTemplet, Object... arguments) {
        Constructor<?> constructor = findConstructor(mViewTemplet, arguments);
        IViewTemplet mTemplet = null;

        try {
            mTemplet = (IViewTemplet)constructor.newInstance(arguments);
        } catch (InstantiationException var5) {
            var5.printStackTrace();
        } catch (IllegalAccessException var6) {
            var6.printStackTrace();
        } catch (InvocationTargetException var7) {
            var7.printStackTrace();
        }

        return (D) mTemplet;
    }

    private static Constructor<?> findConstructor(Class<?> mClass, Object... params) {
        Constructor[] var2 = mClass.getConstructors();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Constructor<?> constructor = var2[var4];
            Class<?>[] paramsTypes = constructor.getParameterTypes();
            if (paramsTypes.length == params.length) {
                boolean match = true;

                for(int i = 0; i < paramsTypes.length; ++i) {
                    if (!paramsTypes[i].isAssignableFrom(params[i].getClass())) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    return constructor;
                }
            }
        }

        return null;
    }

    public static void isDebug(boolean isDebug) {
        DEBUG = isDebug;
    }
}
