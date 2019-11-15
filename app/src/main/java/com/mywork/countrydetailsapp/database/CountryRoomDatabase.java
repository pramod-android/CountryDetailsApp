package com.mywork.countrydetailsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mywork.countrydetailsapp.dao.DataDao;
import com.mywork.countrydetailsapp.dao.ItemDao;
import com.mywork.countrydetailsapp.entity.Item;
import com.mywork.countrydetailsapp.entity.ServiceData;


@Database(entities = {Item.class, ServiceData.class}, version = 1, exportSchema = false)
public abstract class CountryRoomDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract DataDao dataDao();


    private static volatile CountryRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static CountryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CountryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CountryRoomDatabase.class, "country_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
