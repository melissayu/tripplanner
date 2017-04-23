package com.codepathms.cp.tripplannerapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepathms.cp.tripplannerapp.R;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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

    Button btnWalk;
    Button btnDrive;
    Button btnPublic;

    Button btnDollarOne;
    Button btnDollarTwo;
    Button btnDollarThree;

    boolean btnWalkClicked = false;
    boolean btnDriveClicked = false;
    boolean btnPublicClicked = false;

    boolean btnDollarOneClicked = false;
    boolean btnDollarTwoClicked = false;
    boolean btnDollarThreeClicked = false;

    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        currentUser = ParseUser.getCurrentUser();

        getPreferences();
        setupPrefButtons();

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                Intent preferenceIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(preferenceIntent);
            }
        });
    }

    public void setupPrefButtons() {
        btnOutdoor = (Button) findViewById(R.id.btnOutdoor);
        if (btnOutdoorClicked) {
            btnOutdoor.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnOutdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOutdoorClicked = !btnOutdoorClicked;
                if (btnOutdoorClicked) {
                    btnOutdoor.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnOutdoor.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnFood = (Button) findViewById(R.id.btnFood);
        if (btnFoodClicked) {
            btnFood.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFoodClicked = !btnFoodClicked;
                if (btnFoodClicked) {
                    btnFood.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnFood.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnTours = (Button) findViewById(R.id.btnTours);
        if (btnToursClicked) {
            btnTours.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToursClicked = !btnToursClicked;
                if (btnToursClicked) {
                    btnTours.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnTours.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnDrinks = (Button) findViewById(R.id.btnDrinks);
        if (btnDrinksClicked) {
            btnDrinks.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDrinksClicked = !btnDrinksClicked;
                if (btnDrinksClicked) {
                    btnDrinks.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnDrinks.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnSports = (Button) findViewById(R.id.btnSports);
        if (btnSportsClicked) {
            btnSports.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSportsClicked = !btnSportsClicked;
                if (btnSportsClicked) {
                    btnSports.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnSports.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnArts = (Button) findViewById(R.id.btnArts);
        if (btnArtsClicked) {
            btnArts.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnArtsClicked = !btnArtsClicked;
                if (btnArtsClicked) {
                    btnArts.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnArts.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnClubs = (Button) findViewById(R.id.btnClubs);
        if (btnClubsClicked) {
            btnClubs.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClubsClicked = !btnClubsClicked;
                if (btnClubsClicked) {
                    btnClubs.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnClubs.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnShops = (Button) findViewById(R.id.btnShops);
        if (btnShopsClicked) {
            btnShops.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnShopsClicked = !btnShopsClicked;
                if (btnShopsClicked) {
                    btnShops.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnShops.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnHike = (Button) findViewById(R.id.btnHike);
        if (btnHikeClicked) {
            btnHike.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHikeClicked = !btnHikeClicked;
                if (btnHikeClicked) {
                    btnHike.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnHike.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnMusic = (Button) findViewById(R.id.btnMusic);
        if (btnMusicClicked) {
            btnMusic.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMusicClicked = !btnMusicClicked;
                if (btnMusicClicked) {
                    btnMusic.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnMusic.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnMovies = (Button) findViewById(R.id.btnMovies);
        if (btnMoviesClicked) {
            btnMovies.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMoviesClicked = !btnMoviesClicked;
                if (btnMoviesClicked) {
                    btnMovies.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnMovies.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnMuseum = (Button) findViewById(R.id.btnMuseum);
        if (btnMuseumClicked) {
            btnMuseum.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMuseumClicked = !btnMuseumClicked;
                if (btnMuseumClicked) {
                    btnMuseum.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnMuseum.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnDollarOne = (Button) findViewById(R.id.btnDollarOne);
        if (btnDollarOneClicked) {
            btnDollarOne.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnDollarOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDollarOneClicked = !btnDollarOneClicked;
                if (btnDollarOneClicked) {
                    btnDollarOne.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnDollarOne.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnDollarTwo = (Button) findViewById(R.id.btnDollarTwo);
        if (btnDollarTwoClicked) {
            btnDollarTwo.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnDollarTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDollarTwoClicked = !btnDollarTwoClicked;
                if (btnDollarTwoClicked) {
                    btnDollarTwo.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnDollarTwo.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnDollarThree = (Button) findViewById(R.id.btnDollarThree);
        if (btnDollarThreeClicked) {
            btnDollarThree.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
        }
        btnDollarThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDollarThreeClicked = !btnDollarThreeClicked;
                if (btnDollarThreeClicked) {
                    btnDollarThree.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                } else {
                    btnDollarThree.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        btnWalk = (Button) findViewById(R.id.btnWalk);
        btnDrive = (Button) findViewById(R.id.btnDrive);
        btnPublic = (Button) findViewById(R.id.btnPublic);
        if (btnWalkClicked) {
            btnWalk.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
            btnDriveClicked = false;
            btnDrive.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
            btnPublicClicked = false;
            btnPublic.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
        }
        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnWalkClicked) {
                    btnWalkClicked = true;
                    btnWalk.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                    btnDriveClicked = false;
                    btnDrive.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                    btnPublicClicked = false;
                    btnPublic.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        if (btnDriveClicked) {
            btnDrive.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
            btnWalkClicked = false;
            btnWalk.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
            btnPublicClicked = false;
            btnPublic.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
        }
        btnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnDriveClicked) {
                    btnDriveClicked = true;
                    btnDrive.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                    btnWalkClicked = false;
                    btnWalk.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                    btnPublicClicked = false;
                    btnPublic.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });

        if (btnPublicClicked) {
            btnPublic.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
            btnWalkClicked = false;
            btnWalk.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
            btnDriveClicked = false;
            btnDrive.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
        }
        btnPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnPublicClicked) {
                    btnPublicClicked = true;
                    btnPublic.setBackground(getResources().getDrawable(R.drawable.button_shape_selected));
                    btnWalkClicked = false;
                    btnWalk.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                    btnDriveClicked = false;
                    btnDrive.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }

            }
        });
    }

    void getPreferences() {
        String transitPrefs = currentUser.getString("transitPrefs");
        if (transitPrefs != null) {
            if (transitPrefs.equals("Walk")) {
                btnWalkClicked = true;
            } else if (transitPrefs.equals("Drive")) {
                btnDriveClicked = true;
            } else if (transitPrefs.equals("Public")) {
                btnPublicClicked = true;
            }
        }

        List<String> pricePrefs = currentUser.getList("pricePrefs");
        if (pricePrefs != null) {
            for (int j = 0; j < pricePrefs.size(); j++) {
                switch (pricePrefs.get(j)) {
                    case "$":
                        btnDollarOneClicked = true;
                        break;
                    case "$$":
                        btnDollarTwoClicked = true;
                        break;
                    case "$$$":
                        btnDollarThreeClicked = true;
                        break;
                }
            }
        }

        List<String> featurePrefs = currentUser.getList("featurePrefs");
        if (featurePrefs != null) {
            for (int i = 0; i < featurePrefs.size(); i++ ){
                switch (featurePrefs.get(i)) {
                    case "Outdoor":
                        btnOutdoorClicked = true;
                        break;
                    case "Food":
                        btnFoodClicked = true;
                        break;
                    case "Tours":
                        btnToursClicked = true;
                        break;
                    case "Drinks":
                        btnDrinksClicked = true;
                        break;
                    case "Sports":
                        btnSportsClicked = true;
                        break;
                    case "Arts":
                        btnArtsClicked = true;
                        break;
                    case "Clubs":
                        btnClubsClicked = true;
                        break;
                    case "Shops":
                        btnShopsClicked = true;
                        break;
                    case "Hiking":
                        btnHikeClicked = true;
                        break;
                    case "Music":
                        btnMusicClicked = true;
                        break;
                    case "Movies":
                        btnMoviesClicked = true;
                        break;
                    case "Museum":
                        btnMuseumClicked = true;
                        break;
                }
            }
        }
    }

    public void savePreferences() {
        List<String> newFeaturePrefs = new ArrayList<>();
        if (btnOutdoorClicked) newFeaturePrefs.add("Outdoor");
        if (btnFoodClicked) newFeaturePrefs.add("Food");
        if (btnToursClicked) newFeaturePrefs.add("Tours");
        if (btnDrinksClicked) newFeaturePrefs.add("Drinks");
        if (btnSportsClicked) newFeaturePrefs.add("Sports");
        if (btnArtsClicked) newFeaturePrefs.add("Arts");
        if (btnClubsClicked) newFeaturePrefs.add("Clubs");
        if (btnShopsClicked) newFeaturePrefs.add("Shops");
        if (btnHikeClicked) newFeaturePrefs.add("Hiking");
        if (btnMusicClicked) newFeaturePrefs.add("Music");
        if (btnMoviesClicked) newFeaturePrefs.add("Movies");
        if (btnMuseumClicked) newFeaturePrefs.add("Museum");
        currentUser.put("featurePrefs", newFeaturePrefs);

        List<String> newPricePrefs = new ArrayList<>();
        if (btnDollarOneClicked) newPricePrefs.add("$");
        if (btnDollarTwoClicked) newPricePrefs.add("$$");
        if (btnDollarThreeClicked) newPricePrefs.add("$$$");
        currentUser.put("pricePrefs", newPricePrefs);

        String transitPrefs = "";
        if (btnWalkClicked) transitPrefs+="Walk";
        else if (btnDriveClicked) transitPrefs+="Drive";
        else if (btnPublicClicked) transitPrefs+="Public";
        currentUser.put("transitPrefs", transitPrefs);

        try {
            currentUser.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}