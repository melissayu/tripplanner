package com.codepathms.cp.tripplannerapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissa on 4/19/17.
 */

public class SavedItineraryListFragment extends ItineraryListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ArrayList<Itinerary> getItineraries() {
        ParseUser curUser = ParseUser.getCurrentUser();
        ParseRelation<Itinerary> relation = curUser.getRelation("bookmarkedItineraries");
        ParseQuery<Itinerary> relquery = relation.getQuery();
        relquery.findInBackground(new FindCallback<Itinerary>() {
            public void done(List<Itinerary> results, ParseException e) {
                if (e != null) {
                    // There was an error
                } else {
                    itineraryList.clear();
                    // results have all the Posts the current user liked.
                    itineraryList.addAll(results);
                    updateItineraryBookmarks(bookmarkedItineraryIds);
                    itineraryAdapter.notifyDataSetChanged();

                }
            }
        });

/*
        ParseQuery<Itinerary> query = ParseQuery.getQuery("Itinerary");
        query.findInBackground(new FindCallback<Itinerary>() {
            public void done(List<Itinerary> itineraries, ParseException e) {
                if (e == null) {
                    Toast.makeText(getContext(), "#"+itineraries.size(), Toast.LENGTH_SHORT).show();
                    itineraryList.addAll(itineraries);

                    // creating some mock data if DB is empty
                    if (itineraryList.size() == 0) {
//                        itineraryList.addAll(createMockDataItineraries());
                    }
                    itineraryAdapter.notifyDataSetChanged();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
*/
        return (ArrayList<Itinerary>)itineraryList;
    }

}
