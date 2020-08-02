package com.example.doublesucktopdemo;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

//    appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//        @Override
//        public void onStateChanged(AppBarLayout appBarLayout, State state) {
//            if( state == AppBarStateChangeListener.State.EXPANDED ) {
//                //展开状态
//                mTopBar.setTitle("");
//            }else if(state == State.COLLAPSED){
//                //折叠状态
//                mTopBar.setTitle(mAlbumName);
//            }else {
//                //中间状态
//                mTopBar.setTitle("");
//            }
//        }
//    });
//
        private State mCurrentState = State.IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                mCurrentState = State.IDLE;
            }
        }

        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

        public static enum State {
            EXPANDED,
            COLLAPSED,
            IDLE
        }

    }