package com.codepathms.cp.tripplannerapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.fragments.ItineraryDetailHeaderFragment;
import com.codepathms.cp.tripplannerapp.fragments.ItineraryDetailTimelineFragment;
import com.codepathms.cp.tripplannerapp.models.Itinerary;

import org.parceler.Parcels;

public class ItineraryDetailActivity extends AppCompatActivity {
    String itineraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);

//        itinerary = (Itinerary) Parcels.unwrap(getIntent().getParcelableExtra("itinerary"));
        itineraryId = (String) getIntent().getExtras().getString("itineraryId");

        if (savedInstanceState == null) {
            ItineraryDetailHeaderFragment itineraryDetailHeaderFragment = ItineraryDetailHeaderFragment.newInstance(itineraryId);
            ItineraryDetailTimelineFragment itineraryDetailTimelineFragment = ItineraryDetailTimelineFragment.newInstance(itineraryId);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailHeaderContainer, itineraryDetailHeaderFragment, "ITINERARY_DETAIL_HEADER");
            ft.replace(R.id.flDetailTimelineContainer, itineraryDetailTimelineFragment, "ITINERARY_DETAIL_TIMELINE");
            ft.commit();
        }

    }
}
