package com.cn.lenny.androidhighlights.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-15
 */
public class AppExecutors {
    public static final Executor backGroudExecutors;
    public static final UIThreadExecutor mainExecutors;

    private static final ThreadFactory mDefaultFactory = new ThreadFactoryImpl();

    static {
        backGroudExecutors =
                new ThreadPoolExecutor(
                        3,
                        10,
                        30,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(),
                        mDefaultFactory);
        ((ThreadPoolExecutor) backGroudExecutors).allowCoreThreadTimeOut(true);
        mainExecutors = new UIThreadExecutor();
    }
}
