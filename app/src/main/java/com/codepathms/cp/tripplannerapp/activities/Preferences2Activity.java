package com.codepathms.cp.tripplannerapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.models.UserParse;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;
import static com.codepathms.cp.tripplannerapp.R.id.btnClubs;
import static com.codepathms.cp.tripplannerapp.R.id.btnDrinks;
import static com.codepathms.cp.tripplannerapp.R.id.btnMovies;
import static com.codepathms.cp.tripplannerapp.R.id.btnMuseum;
import static com.codepathms.cp.tripplannerapp.R.id.btnMusic;
import static com.codepathms.cp.tripplannerapp.R.id.btnNext;

public class Preferences2Activity extends AppCompatActivity {
    static final String TAG = Preferences2Activity.class.getSimpleName();

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

    static final int UNCLICKED_COLOR = Color.LTGRAY;
    static final int CLICKED_COLOR = Color.BLUE;

    EditText etMessage;
    Button btSend;

    Button btnWalk;
    Button btnDrive;
    Button btnPublic;

    Button btnDollarOne;
    Button btnDollarTwo;
    Button btnDollarThree;

    EditText etCity;

    boolean btnWalkClicked = false;
    boolean btnDriveClicked = false;
    boolean btnPublicClicked = false;

    boolean btnDollarOneClicked = false;
    boolean btnDollarTwoClicked = false;
    boolean btnDollarThreeClicked = false;

    private ArrayList<String> preferences = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences2);

        // User login
        login();

        preferences = (ArrayList<String>) getIntent().getSerializableExtra("preferences");
        Log.e("ABC", preferences.toString());
        etCity = (EditText) findViewById(R.id.etCity);

        for(int i=0; i < preferences.size(); ++i) {
            if(preferences.get(i).contains("City:")) {
                String s = preferences.get(i);
                etCity.setText(s.substring(s.indexOf(":")+1));
            }
        }

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!preferences.contains("City:" + etCity.getText().toString())) {
                    for(int i=0; i < preferences.size(); ++i) {
                        if(preferences.get(i).contains("City:")) {
                            String s = preferences.get(i);
                            preferences.remove(i);
                        }
                    }
                    preferences.add("City:" + etCity.getText().toString());
                }

                UserParse message = new UserParse();
                message.setUsername("bbb");
                message.setUserPreferences(preferences);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(Preferences2Activity.this, "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Failed to save message", e);
                        }
                    }
                });

                Intent preferenceIntent = new Intent(getApplicationContext(), PreferencesActivity.class);

                System.out.println("BBB");
                preferenceIntent.putExtra("preferences", preferences);

                startActivity(preferenceIntent);
            }
        });

        btnWalk = (Button) findViewById(R.id.btnWalk);
        if(preferences.contains("Walk")) {
            btnWalk.setBackgroundColor(CLICKED_COLOR);
            btnWalkClicked = true;
        }
        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnWalkClicked) {
                    btnWalk.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("Walk");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                    btnWalkClicked = false;
                } else {
                    if(!btnDriveClicked && !btnPublicClicked) {
                        btnWalk.setBackgroundColor(CLICKED_COLOR);
                        preferences.remove("Drive");
                        preferences.remove("Public");
                        preferences.add("Walk");
                        btnWalkClicked = true;
                    }
                }
            }
        });

        btnDrive = (Button) findViewById(R.id.btnDrive);
        if(preferences.contains("Drive")) {
            btnDrive.setBackgroundColor(CLICKED_COLOR);
            btnDriveClicked = true;
        }
        btnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDriveClicked) {
                    btnDrive.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("Drive");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                    btnDriveClicked = false;
                } else {
                    if(!btnWalkClicked && !btnPublicClicked) {
                        btnDrive.setBackgroundColor(CLICKED_COLOR);
                        preferences.remove("Walk");
                        preferences.remove("Public");
                        preferences.add("Drive");
                        btnDriveClicked = true;
                    }
                }
            }
        });

        btnPublic = (Button) findViewById(R.id.btnPublic);
        if(preferences.contains("Public")) {
            btnPublic.setBackgroundColor(CLICKED_COLOR);
            btnPublicClicked = true;
        }
        btnPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnPublicClicked) {
                    btnPublic.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("Public");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                    btnPublicClicked = false;
                } else {
                    if(!btnWalkClicked && !btnDriveClicked) {
                        btnPublic.setBackgroundColor(CLICKED_COLOR);
                        preferences.remove("Drive");
                        preferences.remove("Walk");
                        preferences.add("Public");
                        btnPublicClicked = true;
                    }
                }
            }
        });


        btnDollarOne = (Button) findViewById(R.id.btnDollarOne);
        if(preferences.contains("DollarOne")) {
            btnDollarOne.setBackgroundColor(CLICKED_COLOR);
            btnDollarOneClicked = true;
        }
        btnDollarOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDollarOneClicked) {
                    btnDollarOne.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("DollarOne");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                    btnDollarOneClicked = false;
                } else {
                    if(!btnDollarTwoClicked && !btnDollarThreeClicked) {
                        btnDollarOne.setBackgroundColor(CLICKED_COLOR);
                        preferences.remove("DollarTwo");
                        preferences.remove("DollarThree");
                        preferences.add("DollarOne");
                        btnDollarOneClicked = true;
                    }
                }
            }
        });

        btnDollarTwo = (Button) findViewById(R.id.btnDollarTwo);
        if(preferences.contains("DollarTwo")) {
            btnDollarTwo.setBackgroundColor(CLICKED_COLOR);
            btnDollarTwoClicked = true;
        }
        btnDollarTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDollarTwoClicked) {
                    btnDollarTwo.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("DollarTwo");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                    btnDollarTwoClicked = false;
                } else {
                    if(!btnDollarOneClicked && !btnDollarThreeClicked) {
                        btnDollarTwo.setBackgroundColor(CLICKED_COLOR);
                        preferences.remove("DollarOne");
                        preferences.remove("DollarThree");
                        preferences.add("DollarTwo");
                        btnDollarTwoClicked = true;
                    }
                }
            }
        });

        btnDollarThree = (Button) findViewById(R.id.btnDollarThree);
        if(preferences.contains("DollarThree")) {
            btnDollarThree.setBackgroundColor(CLICKED_COLOR);
            btnDollarThreeClicked = true;
        }
        btnDollarThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDollarThreeClicked) {
                    btnDollarThree.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("DollarThree");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                    btnDollarThreeClicked = false;
                } else {
                    if(!btnDollarOneClicked && !btnDollarTwoClicked) {
                        btnDollarThree.setBackgroundColor(CLICKED_COLOR);
                        preferences.remove("DollarTwo");
                        preferences.remove("DollarOne");
                        preferences.add("DollarThree");
                        btnDollarThreeClicked = true;
                    }
                }
            }
        });

    }

    // Get the userId from the cached currentUser object
    void startWithCurrentUser() {
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Anonymous login failed: ", e);
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }

}