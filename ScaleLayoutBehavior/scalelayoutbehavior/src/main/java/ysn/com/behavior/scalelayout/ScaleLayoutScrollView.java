package ysn.com.behavior.scalelayout;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author yangsanning
 * @ClassName ScaleLayoutScrollView
 * @Description 场景：子View的滑动被父ViewGroup给拦截了
 *              用于子类防止父类拦截子类的事件
 * @Date 2020/5/5
 */
public class ScaleLayoutScrollView extends NestedScrollView {
    public ScaleLayoutScrollView(Context context) {
        super(context);
        requestDisallowInterceptTouchEvent(true);
    }

    public ScaleLayoutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    public ScaleLayoutScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        requestDisallowInterceptTouchEvent(true);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

//    在子View的dispatch..或onIntercept..中 DOWN 的时候调用 requestDisallow..(true)，
//    别忘了，在UP或CANCEL的时候，requestDisallow..(false)

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
