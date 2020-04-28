package com.chenxum.paging.db;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

public interface BaseDao<T> {
    @Insert
    void insert(T ... values);
    @Update
    void update(T ... values);
    @Delete
    void delete(T ... values);

    @Insert
    void insert(List<T> values);
    @Update
    void update(List<T> values);
    @Delete
    void delete(List<T> values);

    @RawQuery
    List<T> queryRawQuery(SupportSQLiteQuery query);


}
