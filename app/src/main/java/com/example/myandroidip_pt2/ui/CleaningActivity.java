package com.example.myandroidip_pt2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.Constants;
import com.example.myandroidip_pt2.adapters.CleaningListAdapter;
import com.example.myandroidip_pt2.models.Business;
import com.example.myandroidip_pt2.models.Category;
import com.example.myandroidip_pt2.CleaningArrayAdapter;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.network.YelpApi;
import com.example.myandroidip_pt2.models.YelpBusinessesSearchResponse;
import com.example.myandroidip_pt2.network.YelpClient;
import com.example.myandroidip_pt2.service.YelpService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CleaningActivity extends AppCompatActivity {
    public static final String TAG = CleaningActivity.class.getSimpleName();
    private ArrayList<Cleaning> cleaning = new ArrayList<>();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private CleaningListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        getDryCleaning(location);

    }

    private void getDryCleaning(String location){
        final YelpService yelpService = new YelpService();
        yelpService.findDryCleaning(location, new Callback(){

            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                cleaning = yelpService.processResults(response);
                CleaningActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new CleaningListAdapter(getApplicationContext(), cleaning);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CleaningActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

}