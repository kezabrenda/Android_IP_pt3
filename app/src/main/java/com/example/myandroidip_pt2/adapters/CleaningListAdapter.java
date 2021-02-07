package com.example.myandroidip_pt2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.models.Business;
import com.example.myandroidip_pt2.ui.CleaningDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CleaningListAdapter extends RecyclerView.Adapter<CleaningListAdapter.CleaningViewHolder> {
    private ArrayList<Cleaning> mDryCleaning = new ArrayList<>();
    private Context mContext;

    public CleaningListAdapter(Context context, ArrayList<Cleaning> dryCleaning){
        mContext = context;
        mDryCleaning = dryCleaning;
    }

    @Override
    public CleaningListAdapter.CleaningViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_list_item, parent, false);
        CleaningViewHolder viewHolder = new CleaningViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CleaningListAdapter.CleaningViewHolder holder, int position){
        holder.bindCleaning(mDryCleaning.get(position));
    }

    @Override
    public int getItemCount(){
        return mDryCleaning.size();
    }

    public class CleaningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.dryCleaningImageView) ImageView mDryCleaningImageView;
        @BindView(R.id.cleaningNameTextView) TextView mNameTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public CleaningViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindCleaning(Cleaning cleaning) {
            mNameTextView.setText(cleaning.getName());
            mCategoryTextView.setText(cleaning.getCategories().get(0));
            mRatingTextView.setText("Rating: " + cleaning.getRating() + "/5");
            Picasso.get().load(cleaning.getImageUrl()).into(mDryCleaningImageView);
        }

        @Override
        public void onClick(View v){
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, CleaningDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("dry cleaning", Parcels.wrap(mDryCleaning));
            mContext.startActivity(intent);
        }
    }
}