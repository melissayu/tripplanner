package com.codepathms.cp.tripplannerapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepathms.cp.tripplannerapp.R;
import com.parse.ParseUser;

import java.util.ArrayList;

//import com.codepathms.cp.tripplannerapp.models.ParseUser;

public class PreferencesActivity extends AppCompatActivity {
    static final String TAG = PreferencesActivity.class.getSimpleName();

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

    static final int UNCLICKED_COLOR = Color.LTGRAY;
    static final int CLICKED_COLOR = Color.BLUE;

    EditText etMessage;
    Button btSend;

    Button btnOutdoor;
    Button btnFood;
    Button btnTours;
    Button btnDrinks;
    Button btnSports;
    Button btnArts;
    Button btnClubs;
    Button btnShops;
    Button btnHike;
    Button btnMusic;
    Button btnMovies;
    Button btnMuseum;

    boolean btnOutdoorClicked = false;
    boolean btnFoodClicked = false;
    boolean btnToursClicked = false;
    boolean btnDrinksClicked = false;
    boolean btnSportsClicked = false;
    boolean btnArtsClicked = false;
    boolean btnClubsClicked = false;
    boolean btnShopsClicked = false;
    boolean btnHikeClicked = false;
    boolean btnMusicClicked = false;
    boolean btnMoviesClicked = false;
    boolean btnMuseumClicked = false;

    private ArrayList<String> preferences = new ArrayList<>();

    private ParseUser currentUser;
//    private ParseUser currentParseUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        getPreferences();

        currentUser = ParseUser.getCurrentUser();


//        if(currentParseUser != null) {
//            System.out.println("fef");
//        }
        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferenceIntent = new Intent(getApplicationContext(), Preferences2Activity.class);
                preferenceIntent.putExtra("preferences", preferences);
                startActivity(preferenceIntent);
            }
        });
/*
        btnOutdoor = (Button) findViewById(R.id.btnOutdoor);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("OUTDOOR")) {
            btnOutdoor.setBackgroundColor(CLICKED_COLOR);
            btnOutdoorClicked = true;
        }
        btnOutdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnOutdoorClicked) {
                    btnOutdoor.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("OUTDOOR");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnOutdoor.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("OUTDOOR");
                }
                btnOutdoorClicked = !btnOutdoorClicked;
            }
        });


        btnFood = (Button) findViewById(R.id.btnFood);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("FOOD")) {
            btnFood.setBackgroundColor(CLICKED_COLOR);
            btnFoodClicked = true;
        }
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFoodClicked) {
                    btnFood.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("FOOD");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnFood.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("FOOD");
                }
                btnFoodClicked = !btnFoodClicked;
            }
        });

        btnTours = (Button) findViewById(R.id.btnTours);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("TOURS")) {
            btnTours.setBackgroundColor(CLICKED_COLOR);
            btnToursClicked = true;
        }
        btnTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnToursClicked) {
                    btnTours.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("TOURS");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnTours.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("TOURS");
                }
                btnToursClicked = !btnToursClicked;
            }
        });

        btnDrinks = (Button) findViewById(R.id.btnDrinks);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("DRINKS")) {
            btnDrinks.setBackgroundColor(CLICKED_COLOR);
            btnDrinksClicked = true;
        }
        btnDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDrinksClicked) {
                    btnDrinks.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("DRINKS");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnDrinks.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("DRINKS");
                }
                btnDrinksClicked = !btnDrinksClicked;
            }
        });

        btnSports = (Button) findViewById(R.id.btnSports);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("SPORTS")) {
            btnSports.setBackgroundColor(CLICKED_COLOR);
            btnSportsClicked = true;
        }
        btnSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSportsClicked) {
                    btnSports.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("SPORTS");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnSports.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("SPORTS");
                }
                btnSportsClicked = !btnSportsClicked;
            }
        });

        btnArts = (Button) findViewById(R.id.btnArts);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("ARTS")) {
            btnArts.setBackgroundColor(CLICKED_COLOR);
            btnArtsClicked = true;
        }
        btnArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnArtsClicked) {
                    btnArts.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("ARTS");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnArts.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("ARTS");
                }
                btnArtsClicked = !btnArtsClicked;
            }
        });

        btnClubs = (Button) findViewById(R.id.btnClubs);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("CLUBS")) {
            btnClubs.setBackgroundColor(CLICKED_COLOR);
            btnClubsClicked = true;
        }
        btnClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnClubsClicked) {
                    btnClubs.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("CLUBS");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnClubs.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("CLUBS");
                }
                btnClubsClicked = !btnClubsClicked;
            }
        });

        btnShops = (Button) findViewById(R.id.btnShops);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("SHOPS")) {
            btnShops.setBackgroundColor(CLICKED_COLOR);
            btnShopsClicked = true;
        }
        btnShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnShopsClicked) {
                    btnShops.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("SHOPS");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnShops.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("SHOPS");
                }
                btnShopsClicked = !btnShopsClicked;
            }
        });

        btnHike = (Button) findViewById(R.id.btnHike);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("HIKE")) {
            btnHike.setBackgroundColor(CLICKED_COLOR);
            btnHikeClicked = true;
        }
        btnHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnHikeClicked) {
                    btnHike.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("HIKE");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnHike.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("HIKE");
                }
                btnHikeClicked = !btnHikeClicked;
            }
        });

        btnMusic = (Button) findViewById(R.id.btnMusic);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("MUSIC")) {
            btnMusic.setBackgroundColor(CLICKED_COLOR);
            btnMusicClicked = true;
        }
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnMusicClicked) {
                    btnMusic.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("MUSIC");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnMusic.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("MUSIC");
                }
                btnMusicClicked = !btnMusicClicked;
            }
        });

        btnMovies = (Button) findViewById(R.id.btnMovies);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("MOVIES")) {
            btnMovies.setBackgroundColor(CLICKED_COLOR);
            btnMoviesClicked = true;
        }
        btnMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnMoviesClicked) {
                    btnMovies.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("MOVIES");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnMovies.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("MOVIES");
                }
                btnMoviesClicked = !btnMoviesClicked;
            }
        });

        btnMuseum = (Button) findViewById(R.id.btnMuseum);
        if(currentParseUser != null &&
                currentParseUser.getUserPreferences() != null &&
                currentParseUser.getUserPreferences().contains("MUSEUM")) {
            btnMuseum.setBackgroundColor(CLICKED_COLOR);
            btnMuseumClicked = true;
        }
        btnMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnMuseumClicked) {
                    btnMuseum.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("MUSEUM");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnMuseum.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("MUSEUM");
                }
                btnMuseumClicked = !btnMuseumClicked;
            }
        });
        */

    }

    void getPreferences() {
        /*
        // Construct query to execute
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);

        // get the latest 500 messages, order will show up newest to oldest of this group
        query.orderByAscending("createdAt");

        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        List<ParseUser> messages = null;
        try {
            messages = query.find();
            for(int i=0; i < messages.size(); ++i) {
                if(currentUser.equals(messages.get(i).getUsername())) {
                    currentParseUser = messages.get(i);
                    preferences = (ArrayList<String>) currentParseUser.getUserPreferences();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
    }
}