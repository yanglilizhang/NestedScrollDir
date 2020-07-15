package com.cn.lenny.androidhighlights.executors;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * @author lenny
 * @version 1.0
 * @date 2019-10-15
 */
class ThreadFactoryImpl implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    ThreadFactoryImpl() {
        SecurityManager securityManager = System.getSecurityManager();
        this.group =
                securityManager != null
                        ? securityManager.getThreadGroup()
                        : Thread.currentThread().getThreadGroup();
        this.namePrefix = "stream-task-pool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thead =
                new Thread(
                        this.group,
                        runnable,
                        this.namePrefix + this.threadNumber.getAndIncrement(),
                        0L);
        if (thead.isDaemon()) {
            thead.setDaemon(true);
        }
        if (thead.getPriority() != THREAD_PRIORITY_BACKGROUND) {
            thead.setPriority(THREAD_PRIORITY_BACKGROUND);
        }
        return thead;
    }
}
