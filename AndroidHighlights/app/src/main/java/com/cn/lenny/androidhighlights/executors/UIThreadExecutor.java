package com.cn.lenny.androidhighlights.executors;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

import java.util.concurrent.Executor;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-15
 */
public class UIThreadExecutor implements Executor {
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mainThreadHandler.post(command);
    }

    public void delayExecute(int second, Runnable command) {
        mainThreadHandler.postDelayed(command, second * 1000);
    }

    public void executeImmediatly(Runnable command) {
        mainThreadHandler.postAtFrontOfQueue(command);
    }
    public void removeCallBack(Runnable command){
        mainThreadHandler.removeCallbacks(command);
    }

    /**
     * 在队列空闲时执行任务
     * @param command
     */
    public void executeOnIdel(final Runnable command){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Looper.getMainLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    command.run();
                    return false;
                }
            });
        }else {
            execute(command);
        }
    }
}
