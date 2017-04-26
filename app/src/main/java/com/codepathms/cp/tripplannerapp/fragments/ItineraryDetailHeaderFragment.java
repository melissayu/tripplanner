package com.codepathms.cp.tripplannerapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.activities.MapActivity;
import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.codepathms.cp.tripplannerapp.models.Stop;
import com.codepathms.cp.tripplannerapp.services.Utils;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryDetailHeaderFragment extends Fragment {

    public Itinerary curItinerary;
    public boolean isBookmarked;
    ImageView ivItineraryDetailBookmark;
    ParseUser curUser;

    public static ItineraryDetailHeaderFragment newInstance(String itineraryId) {
        ItineraryDetailHeaderFragment itineraryDetailHeaderFragment = new ItineraryDetailHeaderFragment();
        Bundle args = new Bundle();
        args.putString("itineraryId", itineraryId);
        itineraryDetailHeaderFragment.setArguments(args);
        return itineraryDetailHeaderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_itinerary_detail_header, container, false);
        curUser = ParseUser.getCurrentUser();
        isBookmarked = false;

        final TextView tvItineraryDetailTitle = (TextView) v.findViewById(R.id.tvItineraryDetailTitle);
        final TextView tvItineraryDetailDescription = (TextView) v.findViewById(R.id.tvItineraryDetailDescription);
        final TextView tvItineraryDetailFeatures = (TextView) v.findViewById(R.id.tvItineraryDetailFeatures);
       // final ImageView ivItineraryDetailPhoto = (ImageView) v.findViewById(R.id.ivItineraryDetailPhoto);

        final ImageView ivItineraryDetailMap = (ImageView) v.findViewById(R.id.ivItineraryDetailMap);
        ivItineraryDetailMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra("itineraryId", curItinerary.getObjectId());
                startActivity(intent);
            }
        });

        final ImageView ivItineraryDetailDelete = (ImageView) v.findViewById(R.id.ivItineraryDetailDelete);
        ivItineraryDetailDelete.setVisibility(View.GONE);
        ivItineraryDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this itinerary?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                //Delete all stops plus itinerary
                                try {
                                    String curItineraryId = curItinerary.getObjectId();
                                    curItinerary.delete();
                                    deleteStops(curItineraryId);
                                    getActivity().finish();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });


        ivItineraryDetailBookmark = (ImageView) v.findViewById(R.id.ivItineraryDetailBookmark);

//        Itinerary i = (Itinerary) Parcels.unwrap(getArguments().getParcelable("itinerary"));
        String itineraryId = (String) getArguments().getString("itineraryId");

        ParseQuery<Itinerary> query = ParseQuery.getQuery(Itinerary.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        query.getInBackground(itineraryId, new GetCallback<Itinerary>() {
            public void done(Itinerary itinerary, ParseException e) {
                if (e == null) {
                    // item was found
                    curItinerary = itinerary;
                    tvItineraryDetailTitle.setText(itinerary.getTitle());
                    tvItineraryDetailDescription.setText(itinerary.getDescription());
                    tvItineraryDetailFeatures.setText(Utils.createFeaturesString(itinerary.getFeatures()));

                    if (curItinerary.getParseUser("owner").getObjectId().equals(curUser.getObjectId())) {
                        ivItineraryDetailDelete.setVisibility(View.VISIBLE);
                    } else {
                        ivItineraryDetailDelete.setVisibility(View.GONE);
                    }

                    //tvItineraryDetailFeatures.setText(i.getTags());

                    /*if (itinerary.getImageUrl() == null) {
                        Glide.with(getContext())
                                .load("http://i.imgur.com/XWi7KBJ.jpg") //just a default image
                                .centerCrop()
                                .into(ivItineraryDetailPhoto);
                    } else {
                        Glide.with(getContext())
                                .load(itinerary.getImageUrl())
                                .centerCrop()
                                .into(ivItineraryDetailPhoto);
                    }*/
                }
                ParseRelation<ParseObject> relation = curUser.getRelation("bookmarkedItineraries");
                ParseQuery<ParseObject> relquery = relation.getQuery();
//                relquery.whereKey(curItinerary.getObjectId());
//                relquery.include("Itinerary");
                relquery.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> results, ParseException e) {
                        if (e != null) {
                            // There was an error
                        } else {
                            // results have all the Posts the current user liked.
                            for (int i = 0; i< results.size(); i++) {
//                                if (results.contains(curItinerary)) {
                                if ((results.get(i).getObjectId()).equals(curItinerary.getObjectId())) {
                                    isBookmarked = true;
                                    Glide.with(getContext())
                                            .load(R.drawable.ic_bookmark)
                                            .placeholder(R.drawable.ic_bookmark)
                                            .centerCrop()
                                            .into(ivItineraryDetailBookmark);
                                    break;
                                }

                            }
                        }
                    }
                });
                ivItineraryDetailBookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleBookmark();
                    }
                });


            }
        });


        return v;

    }


    public void deleteStops(String itineraryId) {

        ParseQuery<Stop> query = ParseQuery.getQuery("Stop");
        query.whereEqualTo("itineraryId", itineraryId);
        query.findInBackground(new FindCallback<Stop>() {
            public void done(List<Stop> stops, ParseException e) {
                if (e == null) {
                    ParseObject.deleteAllInBackground(stops);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }


    public void toggleBookmark(){
        ParseRelation<ParseObject> relation = curUser.getRelation("bookmarkedItineraries");
        if (isBookmarked) {
            //Remove bookmark
            relation.remove(curItinerary);
            curUser.saveInBackground();
            Glide.with(getContext())
                    .load(R.drawable.ic_bookmark_border)
                    .placeholder(R.drawable.ic_bookmark_border)
                    .centerCrop()
                    .into(ivItineraryDetailBookmark);
            isBookmarked = false;
        } else {
            //Add bookmark
            relation.add(curItinerary);
            curUser.saveInBackground();
            Glide.with(getContext())
                    .load(R.drawable.ic_bookmark)
                    .placeholder(R.drawable.ic_bookmark)
                    .centerCrop()
                    .into(ivItineraryDetailBookmark);
            isBookmarked = true;
        }
    }
}
