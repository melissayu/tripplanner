package com.codepathms.cp.tripplannerapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.fragments.ItineraryCreateHeaderFragment;
import com.codepathms.cp.tripplannerapp.fragments.ItineraryCreateTimelineFragment;

public class CreateItineraryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_itinerary_detail);

//        Itinerary newItinerary = (Itinerary) Parcels.unwrap(getIntent().getExtras().getParcelable("New_Itinerary"));
        String newItineraryId = (String) getIntent().getStringExtra("New_Itinerary");


        if (savedInstanceState == null) {
            ItineraryCreateHeaderFragment itineraryCreateHeaderFragment = ItineraryCreateHeaderFragment.newInstance(newItineraryId);
            ItineraryCreateTimelineFragment itineraryCreateTimelineFragment = ItineraryCreateTimelineFragment.newInstance(newItineraryId);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flCreateHeaderContainer, itineraryCreateHeaderFragment, "ITINERARY_CREATE_HEADER");
            ft.replace(R.id.flCreateTimelineContainer, itineraryCreateTimelineFragment, "ITINERARY_CREATE_TIMELINE");
            ft.commit();
        }

    }


//    @Override
//    public void onItineraryDetailSave(Itinerary newItinerary) {
//        Intent intent = new Intent();
//        intent.putExtra("NEW_ITINERARY", Parcels.wrap(newItinerary));
//        setResult(RESULT_OK, intent);
//        finish();
//
//    }
}
