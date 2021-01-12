package com.example.myandroidip_pt2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myandroidip_pt2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocateMeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.cleaningPlacesButton) Button mCleaningPlacesButton;
    @BindView(R.id.mobileAgentButton) Button mMobileAgentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_me);
        ButterKnife.bind(this);

        mCleaningPlacesButton.setOnClickListener(this);
        mMobileAgentButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == mCleaningPlacesButton) {
            Intent intent = new Intent(LocateMeActivity.this, CleaningActivity.class);
            String location = intent.getStringExtra("location");
            startActivity(intent);
        }
    }
}