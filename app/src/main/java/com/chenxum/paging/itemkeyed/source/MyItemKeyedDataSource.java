package com.chenxum.paging.itemkeyed.source;

import android.util.Log;

import com.chenxum.paging.db.Cheese;
import com.chenxum.paging.db.CheeseDao;
import com.chenxum.paging.db.MyRoom;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;

/**
 * 本例通过 ItemKeyedDataSource 实现 向上 和 向下 刷新更多
 * 适用场景：先从本地存储中获取数据，当本地无数据是从其他方式（网络）中获取数据
 * 使用 CheeseBoundaryCallback onItemAtEndLoaded
 * DataSource监听到数据库数据变化，又重新回调到 loadInitial 这样导致一个问题，就是会闪屏
 */

public class MyItemKeyedDataSource extends ItemKeyedDataSource<Integer, Cheese> {
    private final String TAG = "MyPageKeyedDataSource";
    private final InvalidationTracker.Observer mObserver;
    private CheeseDao dao = MyRoom.getInstance().getDb().cheeseDao();
    private boolean isLoadMore = false;
    public MyItemKeyedDataSource(RoomDatabase mDb) {
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
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Cheese> callback) {

        Log.d(TAG,"loadInitial"+params.requestedInitialKey);
        if (isLoadMore){
            return;
        }
        String cmd = "select * from cheese where cheese.id >= "+params.requestedInitialKey+" limit "+params.requestedLoadSize;
        Log.d(TAG,"cmd ="+cmd);
        List<Cheese> cheeseList = dao.queryRawQuery(new SimpleSQLiteQuery(cmd));

        callback.onResult(cheeseList);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Cheese> callback) {
        Log.d(TAG,"loadAfter");
        String cmd = "select * from cheese where cheese.id > "+params.key+" limit "+params.requestedLoadSize;
        Log.d(TAG,"cmd ="+cmd);
        List<Cheese> cheeseList = dao.queryRawQuery(new SimpleSQLiteQuery(cmd));
        callback.onResult(cheeseList);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Cheese> callback) {
        Log.d(TAG,"loadBefore");
        String cmd = null;
        cmd = "select count(*) from Cheese where Cheese.id < "+params.key;
        int total = dao.queryRaw(new SimpleSQLiteQuery(cmd));
        Log.d(TAG,"total = "+total);
        cmd = "select * from Cheese where Cheese.id < "+params.key
                +" limit "+params.requestedLoadSize
                +" offset "+(total - params.requestedLoadSize);
        Log.d(TAG,"cmd = "+ cmd);
        // 这里非UI线程
        Log.d(TAG,"thread id "+Thread.currentThread().getId());
        List<Cheese> cheeseList = dao.queryRawQuery(new SimpleSQLiteQuery(cmd));
        callback.onResult(cheeseList);

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Cheese item) {
        return item.getId();
    }
}
