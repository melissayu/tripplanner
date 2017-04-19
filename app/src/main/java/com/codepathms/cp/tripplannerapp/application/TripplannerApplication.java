package com.codepathms.cp.tripplannerapp.application;

import android.app.Application;

import com.codepathms.cp.tripplannerapp.models.Itinerary;
import com.codepathms.cp.tripplannerapp.models.Stop;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by seonglee on 4/12/17.
 */

public class TripplannerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models here
        ParseObject.registerSubclass(Itinerary.class);
        ParseObject.registerSubclass(Stop.class);


        FlowManager.init(new FlowConfig.Builder(this).build());

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("daytripApp") // should correspond to APP_ID env variable
                //.clientKey("codepathmsTripplanner")
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("https://daytrip-cp.herokuapp.com/parse/").build());

    }
}

