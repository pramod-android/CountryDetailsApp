package com.mywork.countrydetailsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mywork.countrydetailsapp.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertItems(List<Item> items);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertItem(Item item);

    @Query("SELECT * from item_table ORDER BY title ASC")
    LiveData<List<Item>> getAllItems();

    @Query("SELECT COUNT(*) FROM item_table")
    LiveData<Integer> getCount();
}
