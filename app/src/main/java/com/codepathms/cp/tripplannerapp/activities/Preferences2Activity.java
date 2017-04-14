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
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import static com.codepathms.cp.tripplannerapp.R.id.btnClubs;
import static com.codepathms.cp.tripplannerapp.R.id.btnDrinks;
import static com.codepathms.cp.tripplannerapp.R.id.btnMovies;
import static com.codepathms.cp.tripplannerapp.R.id.btnMuseum;
import static com.codepathms.cp.tripplannerapp.R.id.btnMusic;

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

        preferences = (ArrayList<String>) getIntent().getSerializableExtra("preferences");
        etCity = (EditText) findViewById(R.id.etCity);

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(etCity.getText().toString());
                preferences.add(etCity.getText().toString());

                System.out.println("AAA");

                Intent preferenceIntent = new Intent(getApplicationContext(), MainActivity.class);

                System.out.println("BBB");
                preferenceIntent.putExtra("preferences", preferences);

                startActivity(preferenceIntent);
            }
        });

        btnWalk = (Button) findViewById(R.id.btnWalk);
        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnWalkClicked) {
                    btnWalk.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("Walk");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnWalk.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("Walk");
                }
                btnWalkClicked = !btnWalkClicked;
            }
        });

        btnDrive = (Button) findViewById(R.id.btnDrive);
        btnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDriveClicked) {
                    btnDrive.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("Drive");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnDrive.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("Drive");
                }
                btnDriveClicked = !btnDriveClicked;
            }
        });

        btnPublic = (Button) findViewById(R.id.btnPublic);
        btnPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnPublicClicked) {
                    btnPublic.setBackgroundColor(UNCLICKED_COLOR);
                    int index = preferences.indexOf("Public");
                    if( index >= 0 ) {
                        preferences.remove(index);
                    }
                } else {
                    btnPublic.setBackgroundColor(CLICKED_COLOR);
                    preferences.add("Public");
                }
                btnPublicClicked = !btnPublicClicked;
            }
        });


        btnDollarOne = (Button) findViewById(R.id.btnDollarOne);
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
                        preferences.add("DollarOne");
                        btnDollarOneClicked = true;
                    }
                }
            }
        });

        btnDollarTwo = (Button) findViewById(R.id.btnDollarTwo);
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
                        preferences.add("DollarTwo");
                        btnDollarTwoClicked = true;
                    }
                }
            }
        });

        btnDollarThree = (Button) findViewById(R.id.btnDollarThree);
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
                        preferences.add("DollarThree");
                        btnDollarThreeClicked = true;
                    }
                }
            }
        });

    }
}
