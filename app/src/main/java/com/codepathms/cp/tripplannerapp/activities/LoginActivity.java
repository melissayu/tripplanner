package com.codepathms.cp.tripplannerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepathms.cp.tripplannerapp.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etLoginUsername = (EditText) findViewById(R.id.etLoginUsername);
        final EditText etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        final EditText etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);



        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(etLoginUsername.getText().toString(), etLoginPassword.getText().toString());
//                Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
//                startActivity(i);
            }
        });

        Button btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupUser(etLoginUsername.getText().toString(),
                        etLoginPassword.getText().toString(),
                        etLoginEmail.getText().toString());
            }
        });
    }


    public void loginUser(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
                    startActivity(i);
                } else {
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
        user.put("featurePrefs", "");
        user.put("transitPrefs", "");
        user.put("pricePrefs", "");
        user.put("location", "");

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {

                    Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
                    startActivity(i);

                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

    }
}
