package com.chenxum.paging.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cheese {
    @PrimaryKey
    private int id;
    private String name;

    public Cheese(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cheese{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
