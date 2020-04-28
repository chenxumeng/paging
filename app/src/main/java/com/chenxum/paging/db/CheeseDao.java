package com.chenxum.paging.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;


@Dao
public abstract class CheeseDao implements BaseDao<Cheese>{
    @Query("select * from cheese")
    public abstract List<Cheese> loadAll();

    @Query("select count(*) from cheese")
    public abstract int count();

    @RawQuery
    public abstract int queryRaw(SupportSQLiteQuery query);

    @Query("select * from cheese where cheese.id=:id")
    public abstract Cheese queyById(int id);

    @Query("select count(*) from cheese where cheese.id >:id")
    public abstract int countgtId(int id);

    @Query("select count(*) from cheese where cheese.id <:id")
    public abstract int countltId(int id);

    @Query("select * from cheese where cheese.id >:id limit :limit")
    public abstract List<Cheese> queryGtId(int id,int limit);

    @Query("select * from cheese where cheese.id <:id limit :limit")
    public abstract List<Cheese> queryLtId(int id,int limit);

    @Query("select * from cheese where cheese.id <:id limit :limit offset :offset")
    public abstract List<Cheese> queryLtId(int id,int limit,int offset);

}
