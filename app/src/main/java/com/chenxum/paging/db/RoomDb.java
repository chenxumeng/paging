package com.chenxum.paging.db;

import androidx.room.Database;

import androidx.room.RoomDatabase;

@Database(entities = {Cheese.class},version = 1,exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    public abstract CheeseDao cheeseDao();
}
