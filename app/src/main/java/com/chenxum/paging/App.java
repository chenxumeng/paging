package com.chenxum.paging;

import android.app.Application;

import com.chenxum.paging.db.Cheese;
import com.chenxum.paging.db.CheeseDao;
import com.chenxum.paging.db.MyRoom;

public class App extends Application {
    private CheeseDao cheeseDao;
    @Override
    public void onCreate() {
        super.onCreate();
        MyRoom.getInstance().init(this);
        cheeseDao = MyRoom.getInstance().getDb().cheeseDao();
        CheeseDefault();
    }

    void CheeseDefault(){
        MyRoom.getInstance().getTransExecutor().execute(()->{
            int count = cheeseDao.count();
            if (count == 0){
                for (int i = 100;i< 200;i++){
                    cheeseDao.insert(new Cheese(i,"cheese item "+i));
                }
            }
        });

    }
}
