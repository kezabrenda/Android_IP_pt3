package com.example.myandroidip_pt2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myandroidip_pt2.models.Business;
import com.example.myandroidip_pt2.models.Category;
import com.example.myandroidip_pt2.CleaningArrayAdapter;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.network.YelpApi;
import com.example.myandroidip_pt2.models.YelpBusinessesSearchResponse;
import com.example.myandroidip_pt2.network.YelpClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CleaningActivity extends AppCompatActivity {
    @BindView(R.id.listView) ListView mListView;
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

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

        YelpApi client = YelpClient.getClient();
        Call<YelpBusinessesSearchResponse> call = client.getDryCleaning(location, "Dry Cleaning");

        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    List<Business> dryCleaningList = response.body().getBusinesses();
                    String[] dryCleaning = new String[dryCleaningList.size()];
                    String[] categories = new String[dryCleaningList.size()];

                    for (int i = 0; i < dryCleaning.length; i++){
                        dryCleaning[i] = dryCleaningList.get(i).getName();
                    }

                    for (int i = 0; i < categories.length; i++) {
                        Category category = dryCleaningList.get(i).getCategories().get(0);
                        categories[i] = category.getTitle();
                    }

                    ArrayAdapter adapter
                            = new CleaningArrayAdapter(CleaningActivity.this, android.R.layout.simple_list_item_1, dryCleaning, categories);
                    mListView.setAdapter(adapter);

                }
            }
            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });
    }
    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mListView.setVisibility(View.VISIBLE);
        mLocationTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}