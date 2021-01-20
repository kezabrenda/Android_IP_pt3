package com.example.myandroidip_pt2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.models.Business;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class CleaningListAdapter extends RecyclerView.Adapter<CleaningListAdapter.DryCleaningViewHolder> {
    private List<Business> mDryCleaning;
    private Context mContext;

    public CleaningListAdapter(Context context, List<Business> dryCleaning) {
        mContext = context;
        mDryCleaning = dryCleaning;
    }

    @Override
    public CleaningListAdapter.DryCleaningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_list_item, parent, false);
        DryCleaningViewHolder viewHolder = new DryCleaningViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CleaningListAdapter.DryCleaningViewHolder holder, int position) {
        holder.bindDryCleaning(mDryCleaning.get(position));
    }

    @Override
    public int getItemCount() {
        return mDryCleaning.size();
    }

    public class DryCleaningViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cleaningNameTextView) TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public DryCleaningViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindDryCleaning(Business dryCleaning) {
            mNameTextView.setText(dryCleaning.getName());
            mCategoryTextView.setText(dryCleaning.getCategories().get(0).getTitle());
            mRatingTextView.setText("Rating: " + dryCleaning.getRating() + "/5");
        }
    }
}