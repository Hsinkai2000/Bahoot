package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login extends AppCompatActivity {
    private EditText emailField;
    private EditText passwordField;
    private String emailStr = "";
    private String passwordStr = "";
    private String userID;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);

        Intent serviceIntent = new Intent(this, WaitingService.class);
        try {
            stopService(serviceIntent);
        } catch (Exception e) {

        }

    }

    public void signIn (View view) throws SQLException {
        emailStr = emailField.getText().toString();
        passwordStr = passwordField.getText().toString();

        if (validate()) {
            new HttpTask().execute("http://192.168.1.11:9999/Bahoot/login?email="
                   + emailStr + "&password=" + passwordStr);
        }

    }

    public void toRegister (View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    private boolean validate() {

        if (emailStr.isEmpty()) {
            Toast.makeText(this, "Please your email",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "Please enter a valid email address",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordStr.isEmpty()) {
            Toast.makeText(this, "Please enter your password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            emailField.setText(extras.getString("email"));
        }
        passwordField.setText(null);
    }

    // Run the HTTP request in a background thread, separating from the main UI thread
    private class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strURLs) {
            URL url = null;
            HttpURLConnection conn = null;
            try {
                url = new URL(strURLs[0]);
                conn = (HttpURLConnection) url.openConnection();
                // Get the HTTP response code (e.g., 200 for "OK", 404 for "Not found")
                // and pass a string description in result to onPostExecute(String result)
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {  // 200

                    String result = conn.getHeaderField("Login");

                    try {
                        userID = conn.getHeaderField("userID");
                        name = conn.getHeaderField("name");

                    } catch (Exception e) {

                    }

                    return result;
                } else {
                    return "Fail (" + responseCode + ")";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // Displays the result of the AsyncTask.
        // The String result is passed from doInBackground().
        @Override
        protected void onPostExecute(String result) {
            Log.d("Error",result);
            if (result.matches("Failure")) {
                Toast.makeText(getApplicationContext(),"Wrong Email or Password",
                        Toast.LENGTH_SHORT).show();
            }
            else if (result.matches("Success")) {
                // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Intent intent = new Intent(getApplicationContext(), RoomCode.class);
                intent.putExtra("userID", userID);
                intent.putExtra("name", name);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),"Error: Server is down",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}