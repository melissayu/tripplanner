package com.codepathms.cp.tripplannerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepathms.cp.tripplannerapp.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Boolean isLoginMode; // false = sign up mode
    EditText etLoginUsername;
    EditText etLoginPassword;
    EditText etLoginEmail;
    TextView tvSignUp;
    TextView tvLogin;
    Button btnLogin;
    Button btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //Initially set as login mode
        isLoginMode = true;

        etLoginUsername = (EditText) findViewById(R.id.etLoginUsername);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        etLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (isLoginMode && actionId == EditorInfo.IME_ACTION_GO) {
                    loginUser(etLoginUsername.getText().toString(),
                            etLoginPassword.getText().toString());
                }
                return true;
            }
        });
        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        etLoginEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    signupUser(etLoginUsername.getText().toString(),
                            etLoginPassword.getText().toString(),
                            etLoginEmail.getText().toString());
                }
                return true;
            }
        });
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        setupViews();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLoginMode();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLoginMode();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(etLoginUsername.getText().toString(), etLoginPassword.getText().toString());
//                Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
//                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupUser(etLoginUsername.getText().toString(),
                        etLoginPassword.getText().toString(),
                        etLoginEmail.getText().toString());
            }
        });
    }

    public void toggleLoginMode(){
        if (isLoginMode) {
            isLoginMode = false;
        } else {
            isLoginMode = true;
        }
        setupViews();
    }

    public void setupViews() {
        if (isLoginMode) { // Hide
            tvSignUp.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            etLoginEmail.setVisibility(View.GONE);
            btnSignup.setVisibility(View.GONE);
        } else {
            tvSignUp.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            etLoginEmail.setVisibility(View.VISIBLE);
            btnSignup.setVisibility(View.VISIBLE);
        }


    }


    public void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }


    public void signupUser(String username, String password, String email){
        // Create the ParseUser
        ParseUser user = new ParseUser();

        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Set custom properties
        user.put("featurePrefs", new ArrayList<String>());
        user.put("transitPrefs", "");
        user.put("pricePrefs", new ArrayList<String>());
        user.put("location", "");

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {

                    Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
                    startActivity(i);

                    // Hooray! Let them use the app now.
                } else {
                    Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

    }
}
