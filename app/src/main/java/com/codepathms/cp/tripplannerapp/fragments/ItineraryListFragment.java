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
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryListFragment extends Fragment {
    private final int REQUEST_CODE = 20;

    List<Itinerary> itineraryList;
    public ItineraryArrayAdapter itineraryAdapter;
    public ListView lvItineraries;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        itineraryList = new ArrayList<>();
        itineraryAdapter = new ItineraryArrayAdapter(getActivity(), itineraryList);

        getItineraries();
//        itineraryList.addAll(getItineraries());
//        itineraryAdapter.notifyDataSetChanged();
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
                    itineraryList.addAll(itineraries);
                    /* creating some mock data if DB is empty*/
                    if (itineraryList.size() == 0) {
//                        itineraryList.addAll(createMockDataItineraries());
                    }
                    itineraryAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return (ArrayList<Itinerary>)itineraryList;
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
