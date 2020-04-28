package com.chenxum.paging.cache;

import java.util.Set;

import androidx.annotation.NonNull;

import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;

public abstract class RoomDataSource<Key,Value> implements ICacheKeyedSource<Key,Value>{
    private final String TAG = "RoomDataSource";
    /**
     * Room 数据库 数据源变化（数据插入，更新，删除） 观察者
     */
    private  InvalidationTracker.Observer mObserver;

    public RoomDataSource(RoomDatabase mDb,String tableName) {
        mObserver = new InvalidationTracker.Observer(tableName) {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
                //invalidate();
            }
        };
        mDb.getInvalidationTracker().addObserver(mObserver);
    }

}
