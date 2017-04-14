package com.codepathms.cp.tripplannerapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.models.Itinerary;

import org.parceler.Parcels;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryDetailHeaderFragment extends Fragment {

    public static ItineraryDetailHeaderFragment newInstance(Itinerary itinerary) {
        ItineraryDetailHeaderFragment itineraryDetailHeaderFragment = new ItineraryDetailHeaderFragment();
        Bundle args = new Bundle();
        args.putParcelable("itinerary", Parcels.wrap(itinerary));
        itineraryDetailHeaderFragment.setArguments(args);
        return itineraryDetailHeaderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_itinerary_detail_header, container, false);

        TextView tvItineraryDetailTitle = (TextView) v.findViewById(R.id.tvItineraryDetailTitle);
        TextView tvItineraryDetailDescription = (TextView) v.findViewById(R.id.tvItineraryDetailDescription);
        TextView tvItineraryDetailFeatures = (TextView) v.findViewById(R.id.tvItineraryDetailFeatures);

        Itinerary i = (Itinerary) Parcels.unwrap(getArguments().getParcelable("itinerary"));
        tvItineraryDetailTitle.setText(i.getTitle());
        tvItineraryDetailDescription.setText(i.getDescription());
        tvItineraryDetailFeatures.setText(i.getTags());

        ImageView ivItineraryDetailPhoto = (ImageView) v.findViewById(R.id.ivItineraryDetailPhoto);
        if (i.getImageUrl() == null) {
            Glide.with(getContext())
                    .load("http://i.imgur.com/XWi7KBJ.jpg") //just a default image
                    .centerCrop()
                    .into(ivItineraryDetailPhoto);
        } else {
            Glide.with(getContext())
                    .load(i.getImageUrl())
                    .centerCrop()
                    .into(ivItineraryDetailPhoto);
        }

        return v;

    }
}
