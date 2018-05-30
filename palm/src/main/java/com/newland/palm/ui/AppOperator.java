package com.newland.palm.ui;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lin
 * @version 2018/5/30 0030
 */
public class AppOperator {

    private static ExecutorService executorService;
    /**
     * 单例线程池
     * @return
     */
    public static Executor getExecutor(){
        if (executorService == null){
            synchronized (AppOperator.class){
                if (executorService == null){
                    executorService = Executors.newFixedThreadPool(6);
                }
            }
        }
        return executorService;
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }

}
