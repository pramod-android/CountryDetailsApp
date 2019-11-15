package com.mywork.countrydetailsapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mywork.countrydetailsapp.R;
import com.mywork.countrydetailsapp.entity.Item;
import com.mywork.countrydetailsapp.entity.ServiceData;
import com.mywork.countrydetailsapp.fragment.ItemsFragment;
import com.mywork.countrydetailsapp.viewmodel.ItemViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemsFragment.OnItemFragmentInteractionListener {
    private ItemViewModel mItemViewModel;
    int count = 0;
    ItemsFragment itemsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mItemViewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                count = integer;

                if (count > 0) {
                    if (itemsFragment == null) {
                        itemsFragment = new ItemsFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout_container, itemsFragment);
                        // Commit the transaction
                        transaction.commit();
                    }
                }
            }
        });
        mItemViewModel.getAllData().observe(this, new Observer<List<ServiceData>>() {
            @Override
            public void onChanged(@Nullable List<ServiceData> data) {

                if (data!=null)
                if (data.size() > 0) {
                    ServiceData serviceData = data.get(data.size() - 1);

                    getSupportActionBar().setTitle(serviceData.getTitle());
                }
            }
        });
    }

    @Override
    public void OnItemClick(Item item) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
               mItemViewModel.refData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
