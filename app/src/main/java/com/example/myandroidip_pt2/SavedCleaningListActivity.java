package com.example.myandroidip_pt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myandroidip_pt2.adapters.FirebaseCleaningViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedCleaningListActivity extends AppCompatActivity {
    private DatabaseReference mCleaningReference;
    private FirebaseRecyclerAdapter<Cleaning, FirebaseCleaningViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mCleaningReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_CLEANING)
                .child(uid);

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Cleaning> options =
                new FirebaseRecyclerOptions.Builder<Cleaning>()
                        .setQuery(mCleaningReference, Cleaning.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Cleaning, FirebaseCleaningViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseCleaningViewHolder firebaseCleaningViewHolder, int position, @NonNull Cleaning cleaning) {
                firebaseCleaningViewHolder.bindCleaning(cleaning);
            }

            @NonNull
            @Override
            public FirebaseCleaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaning_list_item, parent, false);
                return new FirebaseCleaningViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter.stopListening();
        }
    }

}