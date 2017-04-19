package com.codepathms.cp.tripplannerapp.fragments;

import android.util.Log;
import android.widget.Toast;

import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissa on 4/19/17.
 */

public class CreatedItineraryListFragment extends ItineraryListFragment {

    public ArrayList<Itinerary> getItineraries() {
        ParseUser curUser = ParseUser.getCurrentUser();

        ParseQuery<Itinerary> query = ParseQuery.getQuery("Itinerary");
        query.whereEqualTo("owner", curUser);
        query.findInBackground(new FindCallback<Itinerary>() {
            public void done(List<Itinerary> itineraries, ParseException e) {
                if (e == null) {
                    Toast.makeText(getContext(), "#"+itineraries.size(), Toast.LENGTH_SHORT).show();
                    itineraryList.addAll(itineraries);
                    itineraryAdapter.notifyDataSetChanged();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return (ArrayList<Itinerary>)itineraryList;
    }

}
