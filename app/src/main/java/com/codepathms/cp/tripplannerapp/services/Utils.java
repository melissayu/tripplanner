package com.codepathms.cp.tripplannerapp.services;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by melissa on 4/22/17.
 */

public class Utils {

    public static List<String> convertPlaceTypes(List<Integer> placeTypeInts){
        ArrayList<String> placeTypeStrings = new ArrayList<>();
        Set<String> placeTypeSet = new HashSet<>();
        for (int i = 0; i < placeTypeInts.size(); i++) {
            String placeTypeString = placeTypeToString(placeTypeInts.get(i));
            if (!placeTypeString.equals("")) {
                placeTypeSet.add(placeTypeString);
            }
        }
        placeTypeStrings.addAll(placeTypeSet);
        return placeTypeStrings;
    }

    public static String placeTypeToString(Integer placeType) {
        switch (placeType) {
            case Place.TYPE_ACCOUNTING:
                return "Other";
            case Place.TYPE_AIRPORT:
                return "Airport";
            case Place.TYPE_AMUSEMENT_PARK:
                return "Amusement Park";
            case Place.TYPE_BAR:
                return "Drinks";
            case Place.TYPE_CAFE:
                return "Food";
            case Place.TYPE_DEPARTMENT_STORE:
                return "Shops";
            case Place.TYPE_MOVIE_THEATER:
                return "Movies";
            case Place.TYPE_PARK:
                return "Outdoor";
            case Place.TYPE_STORE:
                return "Shops";
            case Place.TYPE_RESTAURANT:
                return "Food";
            case Place.TYPE_BAKERY:
                return "Food";
            case Place.TYPE_ART_GALLERY:
                return "Arts";
            case Place.TYPE_FOOD:
                return "Food";
            case Place.TYPE_SHOPPING_MALL:
                return "Shops";
            case Place.TYPE_MUSEUM:
                return "Museum";
            case Place.TYPE_NIGHT_CLUB:
                return "Clubs";
            default:
                return "";
        }
    }

    public static String createFeaturesString(List<String> featuresList) {
        String featuresString = "Features: ";
        if (featuresList != null) {
            for (int i = 0; i < featuresList.size(); i++) {
                if (i > 0) {
                    featuresString += ", ";
                }
                featuresString += featuresList.get(i);
            }
        }
        return featuresString;
    }

    public static String convertPricePoint(Integer pricePoint) {
        if (pricePoint == 1) { return "$"; }
        else if (pricePoint == 2) { return "$$"; }
        else if (pricePoint == 3) {return "$$$"; }
        else return "";
    }
}
