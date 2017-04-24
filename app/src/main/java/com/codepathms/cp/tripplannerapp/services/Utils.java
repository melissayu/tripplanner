package com.codepathms.cp.tripplannerapp.services;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissa on 4/22/17.
 */

public class Utils {

    public static List<String> convertPlaceTypes(List<Integer> placeTypeInts){
        ArrayList<String> placeTypeStrings = new ArrayList<>();

        for (int i = 0; i < placeTypeInts.size(); i++) {
            String placeTypeString = placeTypeToString(placeTypeInts.get(i));
            if (!placeTypeString.equals("")) {
                placeTypeStrings.add(placeTypeString);
            }
        }

        return placeTypeStrings;
    }

    public static String placeTypeToString(Integer placeType) {
        switch (placeType) {
            case Place.TYPE_ACCOUNTING:
                return "Accounting";
            case Place.TYPE_AIRPORT:
                return "Airport";
            case Place.TYPE_AMUSEMENT_PARK:
                return "Amusement Park";
            case Place.TYPE_BAR:
                return "Bar";
            case Place.TYPE_CAFE:
                return "Cafe";
            case Place.TYPE_DEPARTMENT_STORE:
                return "Department Store";
            case Place.TYPE_MOVIE_THEATER:
                return "Movie Theater";
            case Place.TYPE_PARK:
                return "Park";
            case Place.TYPE_STORE:
                return "Store";
            case Place.TYPE_RESTAURANT:
                return "Restaurant";
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
