package com.mywork.countrydetailsapp.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mywork.countrydetailsapp.api.MyWebServices;
import com.mywork.countrydetailsapp.dao.DataDao;
import com.mywork.countrydetailsapp.dao.ItemDao;
import com.mywork.countrydetailsapp.database.CountryRoomDatabase;
import com.mywork.countrydetailsapp.entity.ServiceData;
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

    private DataDao mDataDao;
    private LiveData<List<ServiceData>> mAllData;

    public ItemRepository(Application application) {
        CountryRoomDatabase db = CountryRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mDataDao =db.dataDao();

        mAllItem = mItemDao.getAllItems();
        mAllData=mDataDao.getAllData();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Item>> getAllItems() {
        refreshData();
        return mAllItem;
    }

    public LiveData<List<ServiceData>> getAllData() {
        return mAllData;
    }


    public void refreshData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        MyWebServices webServices = retrofit.create(MyWebServices.class);

        Call<ServiceData> call = webServices.getAllData();
        call.enqueue(new Callback<ServiceData>() {
            @Override
            public void onResponse(Call<ServiceData> call, Response<ServiceData> response) {

                ServiceData data = response.body();

                if(data!=null) {
                    List<Item> items = data.getItems();
                    if (items != null) {
                        if (items.size() > 0)
                            insertItems(items,data.getTitle());
                    }
                }

            }

            @Override
            public void onFailure(Call<ServiceData> call, Throwable t) {

                Log.i("ItemRepo",t.toString());
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void insertItems(List<Item> items,String title) {
        new insertAsyncTask(mItemDao,mDataDao,title).execute(items);
    }


    private static class insertAsyncTask extends AsyncTask<List<Item>, Void, Void> {

        private ItemDao mAsyncTaskDao;
        private DataDao mAsyncTaskDataDao;
        private  String mTitle;

        insertAsyncTask(ItemDao dao, DataDao dataDao,String title) {
            mAsyncTaskDao = dao;
            mAsyncTaskDataDao=dataDao;
            mTitle=title;

        }

        @Override
        protected Void doInBackground(final List<Item>... params) {


            ServiceData data=new ServiceData();
            data.setTitle(mTitle);
            mAsyncTaskDataDao.insertData(data);

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
