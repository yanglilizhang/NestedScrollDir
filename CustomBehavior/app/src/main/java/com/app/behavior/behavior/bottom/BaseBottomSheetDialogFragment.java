package com.app.behavior.behavior.bottom;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//https://www.cnblogs.com/changyiqiang/p/9353240.html
//https://www.jb51.net/article/185144.htm  *********
public class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior<FrameLayout> bottomSheetBehavior;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) getDialog();
        assert bottomSheetDialog != null;
//        bottomSheetBehavior = bottomSheetDialog.getBehavior();
    }

    public void setPeekHeight(int peekHeight) {
        if (bottomSheetBehavior == null) {
            throw new NullPointerException("behavior is now allow not,you can call method after onActivityCreated");
        }
        bottomSheetBehavior.setPeekHeight(peekHeight);
    }
}