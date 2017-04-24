package com.codepathms.cp.tripplannerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.models.Stop;
import com.codepathms.cp.tripplannerapp.services.Utils;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by melissa on 4/4/17.
 */

public class StopArrayAdapter extends ArrayAdapter<Stop> {
    private Context context;
    ImageView ivStopItemPhoto;
    ParseUser currentUser;
    int currentPosition;
//    GoogleApiClient mGoogleApiClient;

    public StopArrayAdapter(Context context, List<Stop> stops) {
        super(context, android.R.layout.simple_list_item_1, stops);
        this.context = context;

        currentUser = ParseUser.getCurrentUser();
//        mGoogleApiClient = new GoogleApiClient
//                .Builder( getContext() )
//                .addApi( Places.GEO_DATA_API )
//                .addApi( Places.PLACE_DETECTION_API )
//                .build();
//        mGoogleApiClient.connect();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        currentPosition = position;
        final Stop stop =  getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stop, parent, false);
        }

        TextView tvStopTitle = (TextView) convertView.findViewById(R.id.tvStopTitle);
        tvStopTitle.setText(stop.getTitle());
        TextView tvStopAddress = (TextView) convertView.findViewById(R.id.tvStopAddress);
        tvStopAddress.setText(stop.getAddress());
        TextView tvStopFeatures = (TextView) convertView.findViewById(R.id.tvStopFeatures);
        tvStopFeatures.setText(Utils.createFeaturesString(stop.getPlaceTypes()));

        TextView tvNav = (TextView) convertView.findViewById(R.id.tvNavTitle);
        tvNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNav(stop.getAddress());
            }
        });

        ivStopItemPhoto = (ImageView) convertView.findViewById(R.id.ivStopItemPhoto);
        /*if (stop.getImageUrl() != null) {
            Glide.with(context)
                    .load(stop.getImageUrl())
                    .centerCrop()
                    .into(ivStopItemPhoto);
        } else {
            Glide.with(context)
                    .load(R.drawable.gradient) //just a default image
                    .placeholder(R.drawable.gradient)
                    .centerCrop()
                    .into(ivStopItemPhoto);
        }
        //placePhotosAsync(stop.getPlaceId());
        */
        if (stop.bitmap != null) {
            ivStopItemPhoto.setImageBitmap(stop.bitmap);
        } else {
            Glide.with(context)
                    .load(R.drawable.gradient) //just a default image
                    .placeholder(R.drawable.gradient)
                    .centerCrop()
                    .into(ivStopItemPhoto);
        }


        return convertView;
    }


    public void openNav(String addr){
        String transitMode = ""; //Driving dir by default
        if (currentUser.get("transitPrefs") != null) {
            if (currentUser.get("transitPrefs").equals("Walk")) {
                transitMode = "w";
            } else if (currentUser.get("transitPrefs").equals("Drive")) {
                transitMode = "d";
            } else if (currentUser.get("transitPrefs").equals("Public")) {
                transitMode = "r";
            }
        }
        //https://www.google.com/maps/preview?saddr=[insert_from_address_here]&daddr=[insert_to_address_here]&dirflg=[insert_mode_here]
//        w: walking
//        b: bicycling
//        d or h or t: drive
//        r: public transit

//        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude1+","+longitude1+"&daddr="+latitude2+","+longitude2;
        String uri = "http://maps.google.com/maps?f=d&hl=en&daddr="+addr;

        if (currentPosition > 0) {
            Stop prevStop =  getItem(currentPosition-1);
            String prevAddress = prevStop.getAddress();
            uri += "&saddr="+prevAddress;
        }


        if (!transitMode.equals("")) {
            uri += "&dirflg="+transitMode;
        }

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        getContext().startActivity(Intent.createChooser(intent, "Select an application"));

    }


}
