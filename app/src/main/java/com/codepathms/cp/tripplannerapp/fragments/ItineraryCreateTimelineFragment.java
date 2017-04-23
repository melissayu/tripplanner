package com.codepathms.cp.tripplannerapp.fragments;

import android.graphics.Bitmap;
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
import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.codepathms.cp.tripplannerapp.models.Stop;
import com.codepathms.cp.tripplannerapp.services.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by melissa on 4/5/17.
 */

public class ItineraryCreateTimelineFragment extends Fragment{

    ListView lvCreatedStops;
    ArrayList<Stop> stopsList;
    private StopArrayAdapter stopsAdapter;
    String itineraryId;
    Itinerary itinerary;
    int sequence;
    GoogleApiClient mGoogleApiClient;

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

    public static ItineraryCreateTimelineFragment newInstance(String itineraryId) {
        ItineraryCreateTimelineFragment itineraryCreateTimelineFragment = new ItineraryCreateTimelineFragment();
        Bundle args = new Bundle();
        args.putString("itineraryId", itineraryId);
        itineraryCreateTimelineFragment.setArguments(args);
        return itineraryCreateTimelineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_timeline, container, false);

        itineraryId = (String) getArguments().getString("itineraryId");

        // Specify which class to query
        ParseQuery<Itinerary> query = ParseQuery.getQuery(Itinerary.class);
        // Specify the object id
        query.getInBackground(itineraryId, new GetCallback<Itinerary>() {
            public void done(Itinerary item, ParseException e) {
                if (e == null) {
                    itinerary = item;
                } else {
                    // something went wrong
                }
            }
        });


        lvCreatedStops = (ListView) v.findViewById(R.id.lvCreateStops);

        stopsList = new ArrayList<Stop>();
        stopsAdapter = new StopArrayAdapter(getActivity(), stopsList);
        lvCreatedStops.setAdapter(stopsAdapter);

        sequence = 1;
        return v;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                Stop newStop = createNewStop(place);
                stopsList.add(newStop);
                stopsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


    }

    private void placePhotosAsync() {
//        final String placeId = "ChIJrTLr-GyuEmsRBfy61i59si0"; // Australian Cruise Group

        Places.GeoDataApi.getPlacePhotos(mGoogleApiClient, stopsList.get(sequence-1).getPlaceId())
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
                sequence++;
                return;
            }
            stopsList.get(sequence-1).bitmap = placePhotoResult.getBitmap();
            stopsAdapter.notifyDataSetChanged();

            if (sequence == 1) {
                //Save bitmap as itinerary photo
                saveItineraryPhoto(placePhotoResult.getBitmap());
            }
            sequence++;

        }
    };


    public void saveItineraryPhoto(Bitmap bmp) {
        // Ensure bmp has value
        if (bmp == null) {
            Log.d ("Error" , "Problem with image");
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        ParseFile pFile = new ParseFile(itinerary.getObjectId()+".jpg", stream.toByteArray());
        try
        {
            pFile.save();
            itinerary.setImageBitmap(pFile);
            itinerary.saveEventually();
        }
        catch (ParseException e)
        {
            Log.d ("Error" , "Problem saving image");
            e.printStackTrace();
        }

    }

    public Stop createNewStop(Place place) {
//        Itinerary itinerary = (Itinerary) Parcels.unwrap(getArguments().getParcelable("itinerary"));


        final Stop newStop = new Stop(place.getName().toString());
        newStop.setAddress(place.getAddress().toString());
        newStop.setItineraryId(itineraryId);
        newStop.setPlaceId(place.getId().toString());
//        newStop.setLatLng(place.getLatLng().toString());
        newStop.setLat(place.getLatLng().latitude);
        newStop.setLng(place.getLatLng().longitude);
        newStop.setPricePoint(place.getPriceLevel());
        newStop.setSequenceNumber(sequence);

        newStop.setPlaceTypes(Utils.convertPlaceTypes(place.getPlaceTypes()));
        newStop.setOwner(ParseUser.getCurrentUser());
        newStop.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                rollupItineraryDetails(newStop);
            }
        });

//        Stop newStop = new Stop();
//        newStop.setTitle(name);
//        newStop.setLocation(location);
//        newStop.setPlaceId(placeId);
//        newStop.setItineraryId(123);
//        newStop.setSequenceNumber(sequence);
//        newStop.save();
        return newStop;
    }

    public void rollupItineraryDetails(Stop stop) {
        List<String> placeTypes = stop.getPlaceTypes();
        List<String> itineraryFeatures = itinerary.getFeatures();
        Set<String> itineraryFeaturesSet = new HashSet<>();
        if (itineraryFeatures != null) {
            itineraryFeaturesSet.addAll(itineraryFeatures);
        }
        if (placeTypes != null ){
            itineraryFeaturesSet.addAll(placeTypes);
        }
        itinerary.setFeatures(new ArrayList<String>(itineraryFeaturesSet));

        placePhotosAsync();

        itinerary.saveInBackground();
    }

    public Stop createNewStop(String placeId, String name, String location) {
//        Itinerary itinerary = (Itinerary) Parcels.unwrap(getArguments().getParcelable("itinerary"));

        String itineraryId = (String) getArguments().getString("itineraryId");

        Stop newStop = new Stop(name);
        newStop.setAddress(location);
        newStop.setItineraryId(itineraryId);
        newStop.setPlaceId(placeId);
        newStop.setSequenceNumber(sequence);
        newStop.setOwner(ParseUser.getCurrentUser());
        newStop.saveInBackground();

//        Stop newStop = new Stop();
//        newStop.setTitle(name);
//        newStop.setLocation(location);
//        newStop.setPlaceId(placeId);
//        newStop.setItineraryId(123);
//        newStop.setSequenceNumber(sequence);
//        newStop.save();
        return newStop;
    }
}
