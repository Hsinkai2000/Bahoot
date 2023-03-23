package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {

    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText phoneNumberField;

    private Button registerField;

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameField = findViewById(R.id.registerNameField);
        emailField = findViewById(R.id.registerEmailField);
        passwordField = findViewById(R.id.registerPasswordField);
        confirmPasswordField = findViewById(R.id.registerConfirmPasswordField);
        phoneNumberField = findViewById(R.id.registerPhoneNumberField);
    }

    public void register(View view) {
        name = nameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmPasswordField.getText().toString();
        phoneNumber = phoneNumberField.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this,"Please confirm your password",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmPassword.matches(password)) {
            Toast.makeText(this,"Confirmed password not the same",Toast.LENGTH_SHORT).show();
            return;
        }
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this,"Please enter your phone number",Toast.LENGTH_SHORT).show();
            return;
        }

        new HttpTask().execute("http://127.0.0.1:9999/Bahoot/register?name="+name+"&email="+email
                +"&password="+password+"&phoneNumber="+phoneNumber);


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
                    return "OK (" + responseCode + ")";
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

        }
    }

}