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

import java.util.List;

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

        ivItineraryItemBookmark = (ImageView) convertView.findViewById(R.id.ivItineraryItemBookmark);

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
                    for (int i = 0; i< results.size(); i++) {
                        if ((results.get(i).getObjectId()).equals(itinerary.getObjectId())) {
                            Glide.with(getContext())
                                    .load(R.drawable.ic_bookmark)
                                    .placeholder(R.drawable.ic_bookmark)
                                    .centerCrop()
                                    .into(ivItineraryItemBookmark);
                            break;
                        }

                    }
                }
            }
        });

        return convertView;
    }
}
