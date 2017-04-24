package com.codepathms.cp.tripplannerapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.activities.CreateItineraryActivity;
import com.codepathms.cp.tripplannerapp.activities.CreateItineraryDetailActivity;
import com.codepathms.cp.tripplannerapp.activities.ItineraryDetailActivity;
import com.codepathms.cp.tripplannerapp.adapters.ItineraryArrayAdapter;
import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryListFragment extends Fragment {
    private final int REQUEST_CODE = 20;

    List<Itinerary> itineraryList;
    public ItineraryArrayAdapter itineraryAdapter;
    public ListView lvItineraries;

    public Set<String> bookmarkedItineraryIds;
    private ParseUser currentUser;

    @Override
    public void onResume() {
        super.onResume();
        itineraryAdapter.clear();
        getBookmarkedItineraries();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_itinerary_list, container, false);

        //Get Itineraries ListView and set Adapter
        lvItineraries = (ListView) v.findViewById(R.id.lvItineraries);
        lvItineraries.setAdapter(itineraryAdapter);

        lvItineraries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Itinerary selectedItinerary = itineraryList.get(position);
                Intent i = new Intent(getActivity().getApplicationContext(), ItineraryDetailActivity.class);
                i.putExtra("itineraryId", selectedItinerary.getObjectId());
                startActivityForResult(i, REQUEST_CODE);

            }
        });

        FloatingActionButton fabCreate = (FloatingActionButton) v.findViewById(R.id.fabCreate);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), CreateItineraryActivity.class);
                startActivityForResult(i,10);

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getBookmarkedItineraries() {
        bookmarkedItineraryIds.clear();
        ParseUser curUser = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = curUser.getRelation("bookmarkedItineraries");
        ParseQuery<ParseObject> relquery = relation.getQuery();
        relquery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e != null) {
                    // There was an error
                } else {
                    // results have all the Posts the current user liked.
                    for (int i = 0; i< results.size(); i++) {
                        bookmarkedItineraryIds.add(results.get(i).getObjectId());
                    }
                    getItineraries();
                }

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        itineraryList = new ArrayList<>();
        bookmarkedItineraryIds = new HashSet<String>();

        currentUser = ParseUser.getCurrentUser();

        itineraryAdapter = new ItineraryArrayAdapter(getActivity(), itineraryList, bookmarkedItineraryIds);
        getBookmarkedItineraries();
//        getItineraries();
    }


    public void updateItineraryBookmarks(Set<String> bookmarked){
        for (int i = 0; i < itineraryList.size(); i++) {
            if (bookmarked.contains(itineraryList.get(i).getObjectId())){
                itineraryList.get(i).bookmarked = true;
            }
        }

        itineraryAdapter.notifyDataSetChanged();
    }

    public void newItineraryCreated(String newItineraryId) {
//        itineraryList.add(newItinerary);
//        itineraryAdapter.notifyDataSetChanged();

        //Itinerary record was created, now go to CreateDetail to add Stops
        Intent i = new Intent(getActivity().getApplicationContext(), CreateItineraryDetailActivity.class);
        i.putExtra("New_Itinerary", newItineraryId);
        startActivityForResult(i,10);

    }

    public ArrayList<Itinerary> getItineraries() {
        ParseQuery<Itinerary> query = ParseQuery.getQuery("Itinerary");
        query.findInBackground(new FindCallback<Itinerary>() {
            public void done(List<Itinerary> itineraries, ParseException e) {
                if (e == null) {
                    itineraryList.clear();
                    itineraryList.addAll(itineraries);

                    reorderItineraries();
                    /* creating some mock data if DB is empty*/
//                    if (itineraryList.size() == 0) {
//                        itineraryList.addAll(createMockDataItineraries());
//                    }
                    updateItineraryBookmarks(bookmarkedItineraryIds);
                    itineraryAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return (ArrayList<Itinerary>)itineraryList;
    }

    public ArrayList<Itinerary> reorderItineraries() {
        ArrayList<Itinerary> orderedItineraries = new ArrayList<>();

        return orderedItineraries;
    }

    public ArrayList<Itinerary> getItineraries(String queryString) {
        ParseQuery<Itinerary> query = ParseQuery.getQuery("Itinerary");
        query.whereContains("title", queryString);
        query.findInBackground(new FindCallback<Itinerary>() {
            public void done(List<Itinerary> itineraries, ParseException e) {
                if (e == null) {
                    itineraryAdapter.clear();
                    itineraryList.addAll(itineraries);
                    itineraryAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return (ArrayList<Itinerary>)itineraryList;
    }

    public ArrayList<Itinerary> createMockDataItineraries(){
        ArrayList<Itinerary> mockItineraries = new ArrayList<Itinerary>();

        Itinerary it1 = new Itinerary("Dinner and Dessert2");
        it1.setDescription("A nice evening in Los Gatos");
        it1.setOwner(ParseUser.getCurrentUser());
        it1.saveInBackground();
        Itinerary it2 = new Itinerary("Biking and Picnic at the beach2");
        it2.setDescription("Great for active families");
        it2.setOwner(ParseUser.getCurrentUser());
        it2.saveInBackground();

        mockItineraries.add(it1);
        mockItineraries.add(it2);
        return mockItineraries;

    }
}
