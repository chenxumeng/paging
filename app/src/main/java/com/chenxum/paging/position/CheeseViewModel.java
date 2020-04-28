package com.chenxum.paging.position;

import android.util.Log;

import com.chenxum.paging.db.Cheese;
import com.chenxum.paging.db.CheeseDao;
import com.chenxum.paging.db.MyRoom;
import com.chenxum.paging.itemkeyed.source.MyItemKeyedDataSource;
import com.chenxum.paging.position.source.MyPositionalDataSource;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;


public class CheeseViewModel {
    private final String TAG = "CheeseViewModel";
    /**配置**/
    private PagedList.Config config = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(10) // 配置 初始化 加载条数
            .setPageSize(10) // 设置 每页 加载条数
            .setPrefetchDistance(2)
            .setMaxSize(30) // 保证 loadInitial ,loadAfter ,loadBefore 数据可以完整保存
            .setEnablePlaceholders(false)
            .build();
    private CheeseDao dao = MyRoom.getInstance().getDb().cheeseDao();
    private DataSource.Factory<Integer, Cheese> factory = new DataSource.Factory<Integer, Cheese>() {
        @NonNull
        @Override
        public DataSource<Integer, Cheese> create() {
            Log.d(TAG,"DataSource create");
            return  new MyPositionalDataSource(MyRoom.getInstance().getDb());
        }
    };

    private LiveData<PagedList<Cheese>> livePagedList = null;

    public LiveData<PagedList<Cheese>> getLivePagedList() {
        return livePagedList;
    }

    public CheeseViewModel(int initialLoadKey) {
        LivePagedListBuilder<Integer,Cheese> builder = new LivePagedListBuilder<Integer,Cheese>(factory,config)
                 .setInitialLoadKey(initialLoadKey);
                // .setFetchExecutor()// 指定线程执行异步操作
                //.setBoundaryCallback(new CheeseBoundaryCallback());
        livePagedList = builder.build();
    }

    /**
     * 当DataSource 到达边缘，即没有数据可以获取的时候回调这个
     */
    class CheeseBoundaryCallback extends PagedList.BoundaryCallback<Cheese>{
        @Override
        public void onZeroItemsLoaded() {
            // 数据为空时回调这个
            super.onZeroItemsLoaded();
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull Cheese itemAtFront) {
            // 向上滑动到边缘时且DataSource为空时回调
            Log.d(TAG,"onItemAtFrontLoaded");
            new NetThread(false,itemAtFront).start();
        }

        @Override
        public void onItemAtEndLoaded(@NonNull Cheese itemAtEnd) {
            // 向下滑动到边缘且且DataSource为空回调
            Log.d(TAG,"onItemAtEndLoaded"+Thread.currentThread().getId());
            new NetThread(true,itemAtEnd).start();
        }
    }

    /**
     * 模拟网络中读取数据
     */
    class NetThread extends Thread{
        private boolean next;
        private Cheese cheese;
        public NetThread(boolean next,Cheese cheese) {
            this.cheese = cheese;
            this.next = next;
        }

        @Override
        public void run() {
            try{
                Thread.sleep(1000);
                int current = cheese.getId();
                System.out.println("start");
                if (next){
                    List<Cheese> list = new ArrayList<>();
                    for (int i = current+1; i < current + 21; i++){
                        list.add(new Cheese(i,"next item "+i));
                    }
                    dao.insert(list);
                }else {
                    List<Cheese> list = new ArrayList<>();
                    for (int i = current - 20; i < current ; i++){
                        list.add(new Cheese(i,"pre item "+i));
                    }
                    dao.insert(list);
                }
                System.out.println("end");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
