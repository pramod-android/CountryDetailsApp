package com.mywork.countrydetailsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mywork.countrydetailsapp.R;
import com.mywork.countrydetailsapp.entity.Item;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.RepoViewHolder> {

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewTitle, textViewDescription;
        private ImageView imageView;

        private RepoViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);

        }

        public Item getmItem(int position) {
            Item repos = mItems.get(position);
            return repos;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(v, getmItem(getAdapterPosition()));
            }

        }
    }

    private final LayoutInflater mInflater;
    private List<Item> mItems; // Cached copy of repos
    ItemClickListener mClickListener;
    Context mContext;

    public ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclear_item, parent, false);

        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            holder.textViewTitle.setText(current.getTitle());

            holder.textViewDescription.setText(current.getDescription());

            if(URLUtil.isValidUrl(current.getImageHref()))
            Glide.with(mContext).load(current.getImageHref()).into(holder.imageView);

        } else {
            // Covers the case of data not being ready yet.
            holder.textViewTitle.setText("no data");
        }
    }

    public void setRepos(List<Item> repos) {
        mItems = repos;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, Item item);

    }
}
