package com.mywork.countrydetailsapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mywork.countrydetailsapp.api.MyWebServices;
import com.mywork.countrydetailsapp.dao.ItemDao;
import com.mywork.countrydetailsapp.database.CountryRoomDatabase;
import com.mywork.countrydetailsapp.entity.AboutCanada;
import com.mywork.countrydetailsapp.entity.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mywork.countrydetailsapp.constant.Constants.BASE_URL;

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItem;
    private MyWebServices webservice;

    public ItemRepository(Application application) {
        CountryRoomDatabase db = CountryRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItem = mItemDao.getAllItems();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Item>> getAllItems() {
        refreshData();
        return mAllItem;
    }

    private void refreshData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        MyWebServices webServices = retrofit.create(MyWebServices.class);

        Call<AboutCanada> call = webServices.getAllData();
        call.enqueue(new Callback<AboutCanada>() {
            @Override
            public void onResponse(Call<AboutCanada> call, Response<AboutCanada> response) {

                AboutCanada data = response.body();

                if(data!=null) {
                    List<Item> items = data.getItems();
                    if (items != null) {
                        if (items.size() > 0)
                            insertItems(items);
                    }
                }

            }

            @Override
            public void onFailure(Call<AboutCanada> call, Throwable t) {

                Log.i("ItemRepo",t.toString());
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void insertItems(List<Item> items) {
        new insertAsyncTask(mItemDao).execute(items);
    }


    private static class insertAsyncTask extends AsyncTask<List<Item>, Void, Void> {

        private ItemDao mAsyncTaskDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Item>... params) {


            for(Item item:params[0]) {
                mAsyncTaskDao.insertItem(item);
            }


          //  mAsyncTaskDao.insertItems(params[0]);

            return null;
        }
    }
    public LiveData<Integer> getCount() {
        return mItemDao.getCount();
    }


}
