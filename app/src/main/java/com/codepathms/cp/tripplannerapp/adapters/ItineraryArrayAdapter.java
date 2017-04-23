package com.codepathms.cp.tripplannerapp.adapters;

import android.content.Context;
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
import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.codepathms.cp.tripplannerapp.services.Utils;

import java.util.List;
import java.util.Set;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryArrayAdapter extends ArrayAdapter<Itinerary> {
    private Context context;
    ImageView ivItineraryItemBookmark;
    Set<String> bookmarkedItineraryIds;

    public ItineraryArrayAdapter(Context context, List<Itinerary> itineraries, Set<String> bookmarkedItineraryIds) {
        super(context, android.R.layout.simple_list_item_1, itineraries);
        this.context = context;
        this.bookmarkedItineraryIds = bookmarkedItineraryIds;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Itinerary itinerary =  getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_itinerary, parent, false);
        }
        ivItineraryItemBookmark = (ImageView) convertView.findViewById(R.id.ivItineraryItemBookmark);

        Glide.with(getContext())
                .load(R.drawable.ic_bookmark_border)
                .placeholder(R.drawable.ic_bookmark_border)
                .centerCrop()
                .into(ivItineraryItemBookmark);

        TextView tvItineraryTitle = (TextView) convertView.findViewById(R.id.tvItineraryItemTitle);
        tvItineraryTitle.setText(itinerary.getTitle());

        TextView tvItineraryItemFeature = (TextView) convertView.findViewById(R.id.tvItineraryItemFeature);

        tvItineraryItemFeature.setText(Utils.createFeaturesString(itinerary.getFeatures()));


        ImageView ivItineraryItemPhoto = (ImageView) convertView.findViewById(R.id.ivItineraryItemPhoto);
        if (itinerary.getImageBitmap() != null) {
            String imageUrl = itinerary.getImageBitmap().getUrl() ;//live url
            Uri imageUri = Uri.parse(imageUrl);
            Glide.with(context)
                    .load(imageUri)
                    .centerCrop()
                    .into(ivItineraryItemPhoto);
        } else {
            Glide.with(context)
                    .load(R.drawable.gradient) //just a default image
                    .placeholder(R.drawable.gradient)
                    .centerCrop()
                    .into(ivItineraryItemPhoto);

        }
        /*if (itinerary.getImageUrl() == null) {
            Glide.with(context)
                    .load("http://i.imgur.com/XWi7KBJ.jpg") //just a default image
                    .centerCrop()
                    .into(ivItineraryItemPhoto);
        } else {
            Glide.with(context)
                    .load(itinerary.getImageUrl())
                    .centerCrop()
                    .into(ivItineraryItemPhoto);
        }*/

        if (itinerary.bookmarked != null && itinerary.bookmarked) {

//        if (bookmarkedItineraryIds.contains(itinerary.getObjectId())) {
            Glide.with(getContext())
                    .load(R.drawable.ic_bookmark)
                    .placeholder(R.drawable.ic_bookmark)
                    .centerCrop()
                    .into(ivItineraryItemBookmark);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.ic_bookmark_border)
                    .placeholder(R.drawable.ic_bookmark_border)
                    .centerCrop()
                    .into(ivItineraryItemBookmark);
        }

        return convertView;
    }
}
