package com.chenxum.paging.cache;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

public interface ICacheKeyedSource<Key,Value> {
    List<Value> loadBefore(@NonNull ItemKeyedDataSource.LoadParams<Key> params);
    List<Value> loadInitial(@NonNull ItemKeyedDataSource.LoadInitialParams<Key> params);
    List<Value> loadAfter(@NonNull ItemKeyedDataSource.LoadParams<Key> params);
    Key getKey(@NonNull Value item);

}
