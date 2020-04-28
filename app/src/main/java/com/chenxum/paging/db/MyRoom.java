package com.chenxum.paging.db;

import android.content.Context;

import java.util.concurrent.Executor;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class MyRoom {
    private RoomDb db;
    private MyRoom(){

    }
    private static class Holder{
        private static MyRoom instance = new MyRoom();
    }

    public static MyRoom getInstance(){
        return Holder.instance;
    }

    public void init(Context context){
        db = Room.databaseBuilder(context,RoomDb.class,"Beta.db")
                // 考虑兼容的之间的数据库模式一定要手动设置
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build();
    }

    public RoomDb getDb() {
        return db;
    }

    /**
     * 这个是核心线程数为4 的线程池
     * @return
     */
    public Executor getQueryExecutor(){
        return db.getQueryExecutor();
    }

    public Executor getTransExecutor(){
        return db.getTransactionExecutor();
    }
}
