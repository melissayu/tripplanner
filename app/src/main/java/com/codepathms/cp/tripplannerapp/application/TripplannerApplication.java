package com.codepathms.cp.tripplannerapp.application;

import android.app.Application;

import com.codepathms.cp.tripplannerapp.models.User;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;

/**
 * Created by seonglee on 4/12/17.
 */

public class TripplannerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models here
//        ParseObject.registerSubclass(User.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("codepathms") // should correspond to APP_ID env variable
//                .clientKey("codepathmsTripplanner")
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("https://codepathms.herokuapp.com/parse/").build());

    }
}
