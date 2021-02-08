package com.example.myandroidip_pt2.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myandroidip_pt2.Constants;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.Cleaning;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CleaningDetailFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.dryCleaningImageView) ImageView mImageLabel;
    @BindView(R.id.dryCleaningTextView) TextView mNameLabel;
    @BindView(R.id.cleaningNameTextView) TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.websiteTextView) TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView) TextView mPhoneLabel;
    @BindView(R.id.addressTextView) TextView mAddressLabel;
    @BindView(R.id.saveDryCleaningButton) TextView mSaveDryCleaningButton;

    private Cleaning mCleaning;

    public CleaningDetailFragment() {
        // Required empty public constructor
    }

    public static CleaningDetailFragment newInstance(CleaningActivity dryCleaning){
        CleaningDetailFragment cleaningDetailFragment = new CleaningDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("dry cleaning", Parcels.wrap(dryCleaning));
        cleaningDetailFragment.setArguments(args);
        return cleaningDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mCleaning = Parcels.unwrap(getArguments().getParcelable("dry cleaning"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cleaning_detail, container, false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mCleaning.getImageUrl()).into(mImageLabel);
        mNameLabel.setText(mCleaning.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mCleaning.getCategories()));
        mRatingLabel.setText(Double.toString(mCleaning.getRating()) + "/5");
        mPhoneLabel.setText(mCleaning.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mCleaning.getAddress()));
        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mCleaning.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mCleaning.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mCleaning.getLatitude()
                            + "," + mCleaning.getLongitude()
                            + "?q=(" + mCleaning.getName() + ")"));
            startActivity(mapIntent);
        }
        if (v == mSaveDryCleaningButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference cleaningRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_CLEANING)
                    .child(uid);

            DatabaseReference pushRef = cleaningRef.push();
            String pushId = pushRef.getKey();
            mCleaning.setPushId(pushId);
            pushRef.setValue(mCleaning);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}