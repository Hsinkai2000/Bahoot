package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText phoneNumberField;
    private String nameStr;
    private String emailStr;
    private String passwordStr;
    private String confirmPasswordStr;
    private String phoneNumberStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameField = findViewById(R.id.register_name);
        emailField = findViewById(R.id.register_email);
        phoneNumberField = findViewById(R.id.register_phone_number);
        passwordField = findViewById(R.id.register_password);
        confirmPasswordField = findViewById(R.id.register_confirm_password);

    }

    public void register(View view) {
        nameStr = nameField.getText().toString();
        emailStr = emailField.getText().toString();
        passwordStr = passwordField.getText().toString();
        confirmPasswordStr = confirmPasswordField.getText().toString();
        phoneNumberStr = phoneNumberField.getText().toString();

        if (validate()) {
            new HttpTask().execute("http://10.91.253.112:9999/Bahoot/register?name="
                    + nameStr + "&email=" + emailStr + "&password=" + passwordStr
                    + "&phoneNumber=" + phoneNumberStr);
        }
    }

    private boolean validate(){
        if (nameStr.isEmpty()) {
            Toast.makeText(this,"Please enter your name",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (emailStr.isEmpty()) {
            Toast.makeText(this, "Please enter your email",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "Please enter a valid email address",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        Pattern pattern = Pattern.compile("^[689]\\d{7}$");
        Matcher matcher = pattern.matcher(phoneNumberStr);

        if (phoneNumberStr.isEmpty()) {
            Toast.makeText(this,"Please enter your phone number",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!matcher.matches()) {
            Toast.makeText(this,"Please enter a valid phone number",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (passwordStr.isEmpty()) {
            Toast.makeText(this,"Please enter your password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmPasswordStr.isEmpty()) {
            Toast.makeText(this,"Please confirm your password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!confirmPasswordStr.matches(passwordStr)) {
            Toast.makeText(this,"Confirmed password not the same",
                    Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
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

                    String result = conn.getHeaderField("Verification");

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
            if (!result.isEmpty())
                Log.d("Result:",result);

            if (result.matches("emailTaken")) {
                Toast.makeText(getApplicationContext(),"This email address is taken, " +
                                "please try again.",
                        Toast.LENGTH_SHORT).show();
            }
            else if (result.matches("phoneTaken")){
                Toast.makeText(getApplicationContext(),"This phone number is taken, " +
                                "please try again.",
                        Toast.LENGTH_SHORT).show();
            }
            else if(result.matches("Success")){
                Toast.makeText(getApplicationContext(),"Registration Successful!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.putExtra("email", emailStr);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"Error: Unable to verify credentials",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



}