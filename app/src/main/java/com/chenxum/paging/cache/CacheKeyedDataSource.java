package com.chenxum.paging.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;


public class CacheKeyedDataSource<Key,Value> extends ItemKeyedDataSource<Key,Value> {

    private ReentrantLock lock = new ReentrantLock();
    private final ICacheKeyedSource<Key,Value> source;



    public CacheKeyedDataSource(ICacheKeyedSource<Key,Value> source) {
        this.source = source;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Key> params, @NonNull LoadInitialCallback<Value> callback) {
//        List<Value> initList = new SafetyInvoke<Key,Value>(lock,params){
//            @Override
//            protected List<Value> onInvoke(List<Value> inList) {
//                if (inList.size() == 0){
//                    return null;
//                }
//                List<Value> outList = new ArrayList<>();
//                Value initValue = getKeyIndex(getInitParams().requestedInitialKey);
//                if (initValue == null){
//                    return null;
//                }
//                int initIndex = inList.indexOf(initValue);
//                for (int i = initIndex;i< initIndex + params.requestedLoadSize;i++){
//                    outList.add(inList.get(i));
//                }
//                return outList;
//            }
//        }.invoke(virtualList);
        List<Value> currentList = new lockInvoke<Value>(lock){
            @Override
            protected List<Value> onInvoke() {
                return source.loadInitial(params);
            }
        }.invoke();
        callback.onResult(currentList);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Key> params, @NonNull LoadCallback<Value> callback) {
//        List<Value> initList = new SafetyInvoke<Key,Value>(lock,params){
//            @Override
//            protected List<Value> onInvoke(List<Value> inList) {
//                if (inList.size() == 0){
//                    return null;
//                }
//                List<Value> outList = new ArrayList<>();
//                Value initValue = getKeyIndex(getLoadParams().key);
//                if (initValue == null){
//                    return null;
//                }
//                int initIndex = inList.indexOf(initValue);
//                for (int i = initIndex;i< initIndex + params.requestedLoadSize;i++){
//                    outList.add(inList.get(i));
//                }
//                return outList;
//            }
//        }.invoke(virtualList);
        List<Value> currentList = new lockInvoke<Value>(lock){
            @Override
            protected List<Value> onInvoke() {
                return source.loadAfter(params);
            }
        }.invoke();
        callback.onResult(currentList);

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Key> params, @NonNull LoadCallback<Value> callback) {

//        List<Value> initList = new SafetyInvoke<Key,Value>(lock,params){
//            @Override
//            protected List<Value> onInvoke(List<Value> inList) {
//                if (inList.size() == 0){
//                    return null;
//                }
//                List<Value> outList = new ArrayList<>();
//                Value lastValue = getKeyIndex(params.key);
//                if (lastValue == null){
//                    return null;
//                }
//                int lastIndex =  virtualList.indexOf(lastValue);
//                int startIndex = Math.max(lastIndex - params.requestedLoadSize, 0);
//
//                for (int i = startIndex;i< lastIndex;i++){
//                    outList.add(inList.get(i));
//                }
//                return outList;
//            }
//        }.invoke(virtualList);
        List<Value> currentList = new lockInvoke<Value>(lock){
            @Override
            protected List<Value> onInvoke() {
                return source.loadBefore(params);
            }
        }.invoke();
        callback.onResult(currentList);

    }

    @NonNull
    @Override
    public Key getKey(@NonNull Value item) {
        return null;
    }

    private Value getKeyIndex(@NonNull Key inkey){
        Value startValue = null;
//        for (Value v : virtualList){
//            if (inkey.equals(getKey(v))){
//                startValue = v;
//                break;
//            }
//        }
        return startValue;
    }

    /**
     * 对数据源进行修改 增，改，移动
     */
    public void add(Value value){
        new ThreadInvoke(lock){
            @Override
            protected void onInvoke() {
                //virtualList.add(value);
            }
        }.invoke();
    }

    public void add(int offset,Value value){
        new ThreadInvoke(lock){
            @Override
            protected void onInvoke() {
                //virtualList.add(offset,value);
            }
        }.invoke();
    }

    public void swap(int value1,int value2){
        new ThreadInvoke(lock){
            @Override
            protected void onInvoke() {
                //Collections.swap(virtualList,value1,value2);
            }
        }.invoke();
    }

    public void set(int offset,Value value){
        new ThreadInvoke(lock){
            @Override
            protected void onInvoke() {
                //virtualList.set(offset,value);
            }
        }.invoke();
    }

    public void remove(Value value){
        new ThreadInvoke(lock){
            @Override
            protected void onInvoke() {
                //virtualList.remove(value);
            }
        }.invoke();
    }

    public void remove(int offset){
        new ThreadInvoke(lock){
            @Override
            protected void onInvoke() {
                //virtualList.remove(offset);
            }
        }.invoke();
    }


    abstract class ThreadInvoke{
        protected  ReentrantLock lock;

        public ThreadInvoke(ReentrantLock lock) {
            this.lock = lock;
        }

        public final void invoke(){
            ThreadPool.getInstance().getPool().submit(()->{
                lock.lock();
                try{
                    onInvoke();
                }finally {
                    lock.unlock();
                }
            });
        }
        protected abstract void onInvoke();
    }

    abstract class lockInvoke<Value>{
        protected  ReentrantLock lock;

        public lockInvoke(ReentrantLock lock) {
            this.lock = lock;
        }

        public final List<Value> invoke(){
            lock.lock();
            try{
                return onInvoke();
            }finally {
                lock.unlock();
            }
        }
        protected abstract List<Value> onInvoke();
    }

}
