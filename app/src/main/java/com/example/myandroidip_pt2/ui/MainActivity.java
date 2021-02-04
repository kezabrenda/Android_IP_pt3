package com.example.myandroidip_pt2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myandroidip_pt2.Constants;
import com.example.myandroidip_pt2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.findPlacesButton) Button mFindPlacesButton;
    @BindView(R.id.placeLocationEditText) EditText mPlaceLocationEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mFindPlacesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindPlacesButton) {
            String location = mPlaceLocationEditText.getText().toString();
            if(!(location).equals("")) {
                addToSharedPreferences(location);
            }
            Intent intent = new Intent(MainActivity.this, LocateMeActivity.class);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}