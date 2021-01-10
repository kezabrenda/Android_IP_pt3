package com.example.myandroidip_pt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CleaningActivity extends AppCompatActivity {
    @BindView(R.id.listView) ListView mListView;
    @BindView(R.id.locationTextView) TextView mLocationTextView;

    private String[] cleaningPlaces = new String[] {"Shalom Dry Cleaner", "Simply Clean",
            "Squeacky Clear", "Le Neat", "PROPRE", "Freshies"};
    private String[] cleaningSections = new String[] {"adult clothes", "pet items", "children's clothes",
            "house items", "bikes", "cars" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        ButterKnife.bind(this);

        CleaningArrayAdapter adapter = new CleaningArrayAdapter(this, android.R.layout.simple_list_item_1, cleaningPlaces, cleaningSections);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String cleaningPlace = ((TextView)view).getText().toString();
                Toast.makeText(CleaningActivity.this, cleaningPlace, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the cleaning places near: " + location);

    }
}