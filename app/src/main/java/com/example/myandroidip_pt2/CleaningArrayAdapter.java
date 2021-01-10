package com.example.myandroidip_pt2;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CleaningArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mCleaningPlaces;
    private String[] mCleaningSections;


    public CleaningArrayAdapter(Context mContext, int resource, String[] mCleaningPlaces, String[] mCleaningSections) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mCleaningPlaces = mCleaningPlaces;
        this.mCleaningSections = mCleaningSections;
    }

    @Override
    public Object getItem(int position) {
        String cleaningPlace = mCleaningPlaces[position];
        String cleaningSection = mCleaningSections[position];
        return String.format("%s \nCleans Neatly: %s", cleaningPlace, cleaningSection);
    }

    @Override
    public int getCount() {
        return mCleaningPlaces.length;
    }
}
