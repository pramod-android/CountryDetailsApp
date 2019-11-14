package com.mywork.countrydetailsapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mywork.countrydetailsapp.entity.Item;
import com.mywork.countrydetailsapp.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mRepository;

    private LiveData<List<Item>> mAllItems;

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }
    public LiveData<Integer> getCount() { return mRepository.getCount(); }
    public LiveData<List<Item>> getAllItems() { return mAllItems; }

}
