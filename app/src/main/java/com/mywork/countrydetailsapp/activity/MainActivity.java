package com.mywork.countrydetailsapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.mywork.countrydetailsapp.R;
import com.mywork.countrydetailsapp.entity.Item;
import com.mywork.countrydetailsapp.fragment.ItemsFragment;
import com.mywork.countrydetailsapp.viewmodel.ItemViewModel;

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
    }

    @Override
    public void OnItemClick(Item item) {

    }
}
