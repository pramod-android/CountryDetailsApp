package com.mywork.countrydetailsapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mywork.countrydetailsapp.entity.Item;
import com.mywork.countrydetailsapp.entity.ServiceData;

import java.util.List;

@Dao
public interface DataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertData(ServiceData data);

    @Query("SELECT * from data_table")
    LiveData<List<ServiceData>> getAllData();
}
