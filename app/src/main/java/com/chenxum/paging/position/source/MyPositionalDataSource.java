package com.chenxum.paging.position.source;

import android.util.Log;

import com.chenxum.paging.db.Cheese;
import com.chenxum.paging.db.CheeseDao;
import com.chenxum.paging.db.MyRoom;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;

public class MyPositionalDataSource extends PositionalDataSource<Cheese> {
    private final String TAG = "MyPositionalDataSource";
    private final InvalidationTracker.Observer mObserver;
    private CheeseDao dao = MyRoom.getInstance().getDb().cheeseDao();

    public MyPositionalDataSource(RoomDatabase mDb) {
        // 保证 Room 数据库的变化可以通知到DataSource
        mObserver = new InvalidationTracker.Observer("Cheese") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
                Log.d(TAG,"onInvalidated");
                invalidate();
            }
        };
        mDb.getInvalidationTracker().addObserver(mObserver);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Cheese> callback) {
        Log.d(TAG,"requestedStartPosition = "+params.requestedStartPosition);
        Log.d(TAG,"requestedLoadSize = "+params.requestedLoadSize);
        String cmd = "select * from cheese where cheese.id >= "+params.requestedStartPosition+" limit "+params.requestedLoadSize;
        Log.d(TAG,"cmd ="+cmd);
        List<Cheese> cheeseList = dao.queryRawQuery(new SimpleSQLiteQuery(cmd));

        callback.onResult(cheeseList,0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Cheese> callback) {
        Log.d(TAG,"startPosition = "+params.startPosition);
        Log.d(TAG,"loadSize = "+params.loadSize);
        int loadSize = 0;
        if (params.startPosition + params.loadSize > total()){
            loadSize = total() - params.startPosition;
        }else {
            loadSize = params.loadSize;
        }
        String cmd = "select * from Cheese limit "+loadSize+" offset "+params.startPosition;
        Log.d(TAG,"cmd = "+cmd);
        List<Cheese> cheeseList = dao.queryRawQuery(new SimpleSQLiteQuery(cmd));
        callback.onResult(cheeseList);
    }
    private int total(){
        return dao.count();
    }
}
