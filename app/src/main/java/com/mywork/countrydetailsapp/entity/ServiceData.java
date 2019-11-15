package com.mywork.countrydetailsapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "data_table")
public class ServiceData {
    @PrimaryKey
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    @NonNull
    private String title;
    @SerializedName("rows")
    @Expose
    @Ignore
    private List<Item> items = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
