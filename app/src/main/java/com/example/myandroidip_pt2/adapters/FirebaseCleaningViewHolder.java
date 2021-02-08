package com.example.myandroidip_pt2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.Constants;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.ui.CleaningDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseCleaningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseCleaningViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindCleaning(Cleaning cleaning) {
        ImageView dryCleaningImageView = (ImageView) mView.findViewById(R.id.dryCleaningImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.cleaningNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.get().load(cleaning.getImageUrl()).into(dryCleaningImageView);

        nameTextView.setText(cleaning.getName());
        categoryTextView.setText(cleaning.getCategories().get(0));
        ratingTextView.setText("Rating: " + cleaning.getRating() + "/5");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Cleaning> cleaning = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_CLEANING)
                .child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cleaning.add(snapshot.getValue(Cleaning.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, CleaningDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("dry cleaning", Parcels.wrap(cleaning));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
