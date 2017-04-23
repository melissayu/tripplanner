package com.codepathms.cp.tripplannerapp.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.codepathms.cp.tripplannerapp.models.Stop;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    public Itinerary curItinerary;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    String itineraryId;
    ArrayList<Stop> stopsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        itineraryId = getIntent().getExtras().getString("itineraryId");
        stopsList = new ArrayList<>();

        //Get itinerary TODO: DON'T NEED
        ParseQuery<Itinerary> query = ParseQuery.getQuery(Itinerary.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        query.getInBackground(itineraryId, new GetCallback<Itinerary>() {
            public void done(Itinerary itinerary, ParseException e) {
                if (e == null) {
                    // item was found
                    curItinerary = itinerary;
                }

            }
        });

        //Get Stops


        if (TextUtils.isEmpty(getResources().getString(R.string.google_maps_api_key))) {
            throw new IllegalStateException("You forgot to supply a Google Maps API key");
        }

        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }

    }


    public ArrayList<Stop> getStops() {

        ParseQuery<Stop> query = ParseQuery.getQuery("Stop");
        query.whereEqualTo("itineraryId", itineraryId);
        query.orderByAscending("sequence");
        query.findInBackground(new FindCallback<Stop>() {
            public void done(List<Stop> stops, ParseException e) {
                if (e == null) {
                    if (stops.size() > 0) {
                        stopsList.addAll(stops);
                        setMarkers();
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


        return (ArrayList<Stop>)stopsList;
    }


    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            LatLng latLng = new LatLng(37.367066, -121.997270);
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8);
//            map.animateCamera(cameraUpdate);

//            MapDemoActivityPermissionsDispatcher.getMyLocationWithCheck(this);
            getStops();
//            setMarkers();
        } else {
            Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMarkers() {
        IconGenerator iconGenerator = new IconGenerator(MapActivity.this);
        iconGenerator.setStyle(IconGenerator.STYLE_GREEN);

        ArrayList<Marker> markers = new ArrayList<>();

        for (int i = 0; i < stopsList.size(); i++) {
            Stop s = stopsList.get(i);

            /*
            if (i == 0) {
                //LatLng latLng = new LatLng(37.367066, -121.997270);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLat(), s.getLng()), 11);
                map.animateCamera(cameraUpdate);

            }*/
            // Swap text here to live inside speech bubble
            Bitmap bitmap = iconGenerator.makeIcon(s.getSequenceNumber() + ": " + s.getTitle());
            // Use BitmapDescriptorFactory to create the marker
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

            // Set the color of the marker to green
//            BitmapDescriptor defaultMarker =
//                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            LatLng listingPosition = new LatLng(s.getLat(), s.getLng());
            // Create the marker on the fragment
            Marker mapMarker = map.addMarker(new MarkerOptions()
                    .position(listingPosition)
                    .title(s.getSequenceNumber() + ": " + s.getTitle())
                    .snippet(s.getDescription())
                    .icon(icon));

            markers.add(mapMarker);

        }

        //After all markers are added, zoom camera to bounds
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        map.animateCamera(cu);

    }
}
