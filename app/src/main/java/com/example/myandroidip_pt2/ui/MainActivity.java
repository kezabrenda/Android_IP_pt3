package com.example.myandroidip_pt2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myandroidip_pt2.Constants;
import com.example.myandroidip_pt2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //private SharedPreferences mSharedPreferences;
    //private SharedPreferences.Editor mEditor;
    private DatabaseReference mSearchedLocationReference;


    @BindView(R.id.findPlacesButton) Button mFindPlacesButton;
    @BindView(R.id.placeLocationEditText) EditText mPlaceLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface ostrichFont = Typeface.createFromAsset(getAssets(), "fonts/ostrich-regular.ttf");
        mAppNameTextView.setTypeface(ostrichFont);

        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //mEditor = mSharedPreferences.edit();

        mFindPlacesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindPlacesButton) {
            String location = mPlaceLocationEditText.getText().toString();
            saveLocationToFirebase(location);

            //if(!(location).equals("")) {
                //addToSharedPreferences(location);}

            Intent intent = new Intent(MainActivity.this, LocateMeActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }
    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.setValue(location);
    }

    //private void addToSharedPreferences(String location) {
        //mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply(); }
}