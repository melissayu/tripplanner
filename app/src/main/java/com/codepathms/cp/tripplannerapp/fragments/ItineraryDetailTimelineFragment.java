package com.codepathms.cp.tripplannerapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.adapters.StopArrayAdapter;
import com.codepathms.cp.tripplannerapp.models.Stop;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryDetailTimelineFragment extends Fragment {

    ListView lvStops;
    //ArrayList<Stop> stopsList;
    List<Stop> stopList;
    private StopArrayAdapter stopsAdapter;
    GoogleApiClient mGoogleApiClient;
    int curStop;

    public static ItineraryDetailTimelineFragment newInstance(String itineraryId) {
        ItineraryDetailTimelineFragment itineraryDetailTimelineFragment = new ItineraryDetailTimelineFragment();
        Bundle args = new Bundle();
        args.putString("itineraryId", itineraryId);
        itineraryDetailTimelineFragment.setArguments(args);
        return itineraryDetailTimelineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient
                .Builder( getContext() )
                .addApi( Places.GEO_DATA_API )
                .addApi( Places.PLACE_DETECTION_API )
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_itinerary_detail_timeline, container, false);
        lvStops = (ListView) v.findViewById(R.id.lvStops);

        stopList = new ArrayList<Stop>();
        stopsAdapter = new StopArrayAdapter(getActivity(), stopList);
        lvStops.setAdapter(stopsAdapter);

        getStops();
//        stopList.addAll(getStops());
//        stopsAdapter.notifyDataSetChanged();

        return v;
    }

    public ArrayList<Stop> getStops() {

        String itineraryId = (String) getArguments().getString("itineraryId");

        ParseQuery<Stop> query = ParseQuery.getQuery("Stop");
        query.whereEqualTo("itineraryId", itineraryId);
        query.orderByAscending("sequence");
        query.findInBackground(new FindCallback<Stop>() {
            public void done(List<Stop> stops, ParseException e) {
                if (e == null) {
                    stopList.addAll(stops);
                    /* creating some mock data if DB is empty*/
                    if (stopList.size() == 0) {
//                        stopList.addAll(createMockDataStops());
                    }
                    getStopsPhotos();
//                    stopsAdapter.notifyDataSetChanged();

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


        return (ArrayList<Stop>)stopList;
    }

    public void getStopsPhotos(){
        if (curStop < stopList.size()) {
            placePhotosAsync();

        }
//        for (int i = 0; i < stopList.size(); i++) {
//            curStop = stopList.get(i);
//            placePhotosAsync(curStop);
//        }
    }

    /**
     * Load a bitmap from the photos API asynchronously
     * by using buffers and result callbacks.
     */
    private void placePhotosAsync() {
//        final String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0"; // Australian Cruise Group
        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, stopList.get(curStop).getPlaceId())
                .setResultCallback(new ResultCallback<PlacePhotoMetadataResult>() {

                    @Override
                    public void onResult(PlacePhotoMetadataResult photos) {
                        if (!photos.getStatus().isSuccess()) {
                            return;
                        }

                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                        if (photoMetadataBuffer.getCount() > 0) {
                            // Display the first bitmap in an ImageView in the size of the view
                            photoMetadataBuffer.get(0)
                                    .getPhoto(mGoogleApiClient)
                                    .setResultCallback(mDisplayPhotoResultCallback);
                        }
                        photoMetadataBuffer.release();
                    }
                });
    }

    private ResultCallback<PlacePhotoResult> mDisplayPhotoResultCallback
            = new ResultCallback<PlacePhotoResult>() {
        @Override
        public void onResult(PlacePhotoResult placePhotoResult) {
            if (!placePhotoResult.getStatus().isSuccess()) {
                return;
            }
            stopList.get(curStop).bitmap = placePhotoResult.getBitmap();
            stopsAdapter.notifyDataSetChanged();
            curStop++;
            getStopsPhotos();

//            ivStopItemPhoto.setImageBitmap(placePhotoResult.getBitmap());
        }
    };


    public ArrayList<Stop> createMockDataStops() {
        ArrayList<Stop> mockStops = new ArrayList<Stop>();
        Stop stop1 = new Stop("Fancy Restaurant");
        stop1.setAddress("11 College Ave, Los Gatos, CA 95030, USA");
        stop1.setOwner(ParseUser.getCurrentUser());
        stop1.saveInBackground();

        Stop stop2 = new Stop("Ice Cream Shop");
        stop2.setAddress("2948 College Ave, Berkeley, CA 94705, USA");
        stop2.setOwner(ParseUser.getCurrentUser());
        stop2.saveInBackground();


        mockStops.add(stop1);
        mockStops.add(stop2);
        return mockStops;
    }

    /*
    public ArrayList<Stop> getStops() {
        //TODO: Get stops from Parse DB

        Itinerary parentItinerary = (Itinerary) Parcels.unwrap(getArguments().getParcelable("itinerary"));
        List<Stop> stopList = null;
//        List<Stop> stopList = SQLite.select().from(Stop.class).where(Stop_Table.itineraryId.eq(parentItinerary.getId())).queryList();

        //ArrayList<Stop> stopsList = new ArrayList<>();

        if (stopList.size() == 0) {
            //some Stop mock data
            Stop stop1 = new Stop();
            stop1.setTitle("Fancy Restaurant");
            stop1.setLocation("11 College Ave, Los Gatos, CA 95030, USA");
            Stop stop2 = new Stop();
            stop2.setTitle("Ice Cream Shop");
            stop2.setLocation("2948 College Ave, Berkeley, CA 94705, USA");
            stopsList.add(stop1);
            stopsList.add(stop2);
            return stopsList;

        }

        return (ArrayList<Stop>) stopList;

    }
    */
}
