package com.mywork.countrydetailsapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "item_table")
public class Item {
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    @SerializedName("id")
//    @Expose
//    @NonNull
//    private int id;

    @PrimaryKey
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    @NonNull
    private String title;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "imageHref")
    @SerializedName("imageHref")
    @Expose
    private String imageHref;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

}
