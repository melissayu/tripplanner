package com.codepathms.cp.tripplannerapp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.raizlabs.android.dbflow.annotation.Column;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by seonglee on 4/15/17.
 */

@ParseClassName("UserParse")
public class UserParse extends ParseObject {
    public static final String USER_ID_KEY = "userId";
    public static final String USER_NAME_KEY = "username";
    public static final String USER_PREFERENCES_KEY = "userPreferences";
    public static final String IMAGE_URL_KEY = "imageUrl";
    public static final String SAVED_ITINERARIES_KEY = "savedItineraries";
    public static final String VISITED_ITINERARIES_KEY = "visitedItineraries";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public String getUsername() {
        return getString(USER_NAME_KEY);
    }

    public void setUsername(String username) {
        put(USER_NAME_KEY, username);
    }

    public List<String> getUserPreferences() {
        return getList(USER_PREFERENCES_KEY);
    }

    public void setUserPreferences(ArrayList<String> userPreferences) {
        put(USER_PREFERENCES_KEY, userPreferences);
    }


    public String getImageUrl() {
        return getString(IMAGE_URL_KEY);
    }

    public void setImageUrl(String imageUrl) {
        put(IMAGE_URL_KEY, imageUrl);
    }

    public String getSavedItineraries() {
        return getString(SAVED_ITINERARIES_KEY);
    }

    public void setSavedItineraries(String savedItineraries) {
        put(SAVED_ITINERARIES_KEY, savedItineraries);
    }

    public String getVisitedItineraries() {
        return getString(VISITED_ITINERARIES_KEY);
    }

    public void setVisitedItineraries(String visitedItineraries) {
        put(VISITED_ITINERARIES_KEY, visitedItineraries);
    }
}