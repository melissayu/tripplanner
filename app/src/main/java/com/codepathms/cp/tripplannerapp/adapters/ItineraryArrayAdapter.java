package com.codepathms.cp.tripplannerapp.adapters;

import android.content.Context;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by melissa on 4/4/17.
 */

public class ItineraryArrayAdapter extends ArrayAdapter<Itinerary> {
    private Context context;
    ImageView ivItineraryItemBookmark;

    public ItineraryArrayAdapter(Context context, List<Itinerary> itineraries) {
        super(context, android.R.layout.simple_list_item_1, itineraries);
        this.context = context;
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

        ImageView ivItineraryItemPhoto = (ImageView) convertView.findViewById(R.id.ivItineraryItemPhoto);
        if (itinerary.getImageUrl() == null) {
            Glide.with(context)
                    .load("http://i.imgur.com/XWi7KBJ.jpg") //just a default image
                    .centerCrop()
                    .into(ivItineraryItemPhoto);
        } else {
            Glide.with(context)
                    .load(itinerary.getImageUrl())
                    .centerCrop()
                    .into(ivItineraryItemPhoto);
        }

        ParseUser curUser = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = curUser.getRelation("bookmarkedItineraries");
        ParseQuery<ParseObject> relquery = relation.getQuery();
//                relquery.whereKey(curItinerary.getObjectId());
        relquery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e != null) {
                    // There was an error
                } else {
                    // results have all the Posts the current user liked.
                    Set<String> objIds= new HashSet<String>();
                    for (int i = 0; i< results.size(); i++) {
                        objIds.add(results.get(i).getObjectId());
                    }
                    if (objIds.contains(itinerary.getObjectId())) {
                        Glide.with(getContext())
                                .load(R.drawable.ic_bookmark)
                                .placeholder(R.drawable.ic_bookmark)
                                .centerCrop()
                                .into(ivItineraryItemBookmark);
                    }

                }
            }
        });

        return convertView;
    }
}
