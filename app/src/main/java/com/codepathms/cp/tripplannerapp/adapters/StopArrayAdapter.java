package com.codepathms.cp.tripplannerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by melissa on 4/4/17.
 */

public class StopArrayAdapter extends ArrayAdapter<Stop> {
    private Context context;
    ImageView ivStopItemPhoto;
    ParseUser currentUser;
    int currentPosition;
    Stop prevStop;
    String transitMode;
    String distString;
    TextView tvNav;

    //    GoogleApiClient mGoogleApiClient;

    public StopArrayAdapter(Context context, List<Stop> stops) {
        super(context, android.R.layout.simple_list_item_1, stops);
        this.context = context;

        currentUser = ParseUser.getCurrentUser();
        transitMode = currentUser.getString("transitPrefs");

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
        prevStop = null;
        distString = "Navigate to stop";


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stop, parent, false);
        }

        if (currentPosition > 0) {
            prevStop =  getItem(currentPosition-1);
        }


        TextView tvStopTitle = (TextView) convertView.findViewById(R.id.tvStopTitle);
        tvStopTitle.setText(stop.getTitle());
        TextView tvStopAddress = (TextView) convertView.findViewById(R.id.tvStopAddress);
        tvStopAddress.setText(stop.getAddress());
        TextView tvStopFeatures = (TextView) convertView.findViewById(R.id.tvStopFeatures);
        tvStopFeatures.setText(Utils.createFeaturesString(stop.getPlaceTypes()));

        tvNav = (TextView) convertView.findViewById(R.id.tvNavTitle);
        if (prevStop != null) {
            setDurationText(prevStop.getAddress(), stop.getAddress());
        } else {
            tvNav.setText(distString);
        }
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

    public void setDurationText(String sourceAddr, String destAddr) {
        if (sourceAddr == null) {
            tvNav.setText("Navigate to stop");
        }
        else {
            String mode;
            if (transitMode == null) {
                transitMode = "Drive";
                mode = "driving";
            } else if (transitMode.equals("Walk")) {
                mode = "walking";
            } else if (transitMode.equals("Transit")) {
                mode = "transit";
            } else {
                transitMode = "Drive";
                mode = "driving";
            }
            distString = "";

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("origins", sourceAddr);
            params.put("destinations", destAddr);
            params.put("mode", mode);
            client.get("http://maps.googleapis.com/maps/api/distancematrix/json", params, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.d("distance failure", responseString);

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Log.d("distance success", responseString);
                            try {
                                JSONObject response = new JSONObject(responseString);
                                JSONArray rows = response.getJSONArray("rows");
                                JSONObject row = rows.getJSONObject(0);
                                JSONArray elements = row.getJSONArray("elements");
                                JSONObject element = elements.getJSONObject(0);
                                String duration = element.getJSONObject("duration").getString("text");
                                distString = "" + transitMode + " approx " + duration;
                                tvNav.setText(distString);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        }
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

//        if (currentPosition > 0) {
//            Stop prevStop =  getItem(currentPosition-1);
//            String prevAddress = prevStop.getAddress();
//            uri += "&saddr="+prevAddress;
//        }
        if (prevStop != null) {
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
