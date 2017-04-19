package com.codepathms.cp.tripplannerapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by melissa on 4/5/17.
 */

public class ItineraryCreateHeaderFragment extends Fragment{

/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnItineraryCreatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnItineraryCreatedListener");
        }

    }
*/
    public static ItineraryCreateHeaderFragment newInstance(String newItineraryId) {
        ItineraryCreateHeaderFragment itineraryCreateHeaderFragment = new ItineraryCreateHeaderFragment();
        Bundle args = new Bundle();
        args.putString("itineraryId", newItineraryId);
        itineraryCreateHeaderFragment.setArguments(args);
        return itineraryCreateHeaderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_header, container, false);

        final EditText etCreateDescription = (EditText) v.findViewById(R.id.etCreateDescription);
        final EditText etCreateTitle = (EditText) v.findViewById(R.id.etCreateTitle);

//        Itinerary itinerary = (Itinerary) Parcels.unwrap(getArguments().getParcelable("itinerary"));
        String itineraryId = (String) getArguments().getString("itineraryId");

        ParseQuery<Itinerary> query = ParseQuery.getQuery(Itinerary.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        query.getInBackground(itineraryId, new GetCallback<Itinerary>() {
            public void done(Itinerary itinerary, ParseException e) {
                if (e == null) {
                    // item was found
                    etCreateDescription.setText(itinerary.getDescription());
                    etCreateTitle.setText(itinerary.getTitle());
                }
            }
        });

        Button btnCreateDone = (Button) v.findViewById(R.id.btnCreateDone);
        btnCreateDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                //Itinerary itinerary = saveItinerary();
                //mCallback.onItinerarySave(itinerary);
            }
        });

        return v;

    }


}
