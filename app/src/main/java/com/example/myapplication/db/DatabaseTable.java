package com.example.myapplication.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class DatabaseTable {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NotNull
    int itemId;
    private String menuName;
    private String firstLevelFlags;
    private String secondLevelFlags;

    public DatabaseTable(@NotNull int itemId, String menuName, String firstLevelFlags, String secondLevelFlags) {
        this.itemId = itemId;
        this.menuName = menuName;
        this.firstLevelFlags = firstLevelFlags;
        this.secondLevelFlags = secondLevelFlags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getFirstLevelFlags() {
        return firstLevelFlags;
    }

    public void setFirstLevelFlags(String firstLevelFlags) {
        this.firstLevelFlags = firstLevelFlags;
    }

    public String getSecondLevelFlags() {
        return secondLevelFlags;
    }

    public void setSecondLevelFlags(String secondLevelFlags) {
        this.secondLevelFlags = secondLevelFlags;
    }
}
