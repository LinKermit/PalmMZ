package com.newland.lonely;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lin
 * @version 2018/8/24 0024
 */
public class AppOperator {

    public static ExecutorService executorService;
    public static ExecutorService getExecutor(){
        if (executorService == null){
            executorService = Executors.newFixedThreadPool(6);
        }
        return executorService;
    }

    public static void runOnThread(Runnable runnable){
        getExecutor().execute(runnable);
    }
}
