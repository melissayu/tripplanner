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

import java.util.List;

/**
 * Created by melissa on 4/4/17.
 */

public class StopArrayAdapter extends ArrayAdapter<Stop> {
    private Context context;
    ImageView ivStopItemPhoto;
//    GoogleApiClient mGoogleApiClient;

    public StopArrayAdapter(Context context, List<Stop> stops) {
        super(context, android.R.layout.simple_list_item_1, stops);
        this.context = context;

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
//        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude1+","+longitude1+"&daddr="+latitude2+","+longitude2;
        String uri = "http://maps.google.com/maps?f=d&hl=en&daddr="+addr;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        getContext().startActivity(Intent.createChooser(intent, "Select an application"));

    }


}
