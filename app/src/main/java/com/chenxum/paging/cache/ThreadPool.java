package com.chenxum.paging.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public final class ThreadPool {
    private ExecutorService cachedThreadPool = Executors.newFixedThreadPool(4);

    private static class Holder{
        private final static  ThreadPool instance = new ThreadPool();
    }

    private ThreadPool(){

    }

    public static ThreadPool getInstance(){
        return Holder.instance;
    }

    public ExecutorService getPool(){
        return cachedThreadPool;
    }



}
