package com.chenxum.paging.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import androidx.paging.ItemKeyedDataSource;

abstract class SafetyInvoke<Key,Value>{
    protected   ReentrantLock lock;
    private ItemKeyedDataSource.LoadInitialParams<Key> initParams;
    private ItemKeyedDataSource.LoadParams<Key> loadParams;

    public SafetyInvoke(ReentrantLock lock) {
        this.lock = lock;
    }

    public SafetyInvoke(ReentrantLock lock, ItemKeyedDataSource.LoadInitialParams<Key> initParams) {
        this.lock = lock;
        this.initParams = initParams;
    }

    public SafetyInvoke(ReentrantLock lock, ItemKeyedDataSource.LoadParams<Key> loadParams) {
        this.lock = lock;
        this.loadParams = loadParams;
    }

    public final List<Value> invoke(List<Value> inList){
        List<Value> outList = new ArrayList<>();
        lock.lock();
        try{
            outList = onInvoke(inList);
        }finally {
            lock.unlock();
        }
        return outList;
    }

    protected abstract List<Value> onInvoke(List<Value> inList);

    protected ItemKeyedDataSource.LoadInitialParams<Key> getInitParams() {
        return initParams;
    }

    protected ItemKeyedDataSource.LoadParams<Key> getLoadParams() {
        return loadParams;
    }
}
