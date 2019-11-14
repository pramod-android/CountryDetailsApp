package com.mywork.countrydetailsapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mywork.countrydetailsapp.R;
import com.mywork.countrydetailsapp.adapter.ItemListAdapter;
import com.mywork.countrydetailsapp.entity.Item;
import com.mywork.countrydetailsapp.viewmodel.ItemViewModel;

import java.util.List;

public class ItemsFragment extends Fragment implements ItemListAdapter.ItemClickListener {
    private ItemViewModel mItemViewModel;

    private Context mContext;

    private OnItemFragmentInteractionListener mListener;

    public ItemsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ItemsFragment newInstance(String param1, String param2) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_items, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ItemListAdapter adapter = new ItemListAdapter(mContext);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable final List<Item> repos) {
                // Update the cached copy of the words in the adapter.
                adapter.setRepos(repos);
            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        if (context instanceof OnItemFragmentInteractionListener) {
            mListener = (OnItemFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, Item item) {
        if (mListener != null) {
            mListener.OnItemClick(item);
        }
    }

    public interface OnItemFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnItemClick(Item item);
    }
}
