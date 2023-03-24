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
    private String sqlStr = "";
    //private SQL sql = new SQL();
    private ResultSet resultSet;

    public Login() throws SQLException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginEmailField);
        passwordField = findViewById(R.id.loginPasswordField);

    }

    public void signIn (View view) throws SQLException {
        emailStr = emailField.getText().toString();
        passwordStr = passwordField.getText().toString();

        if (validate()) {
            new HttpTask().execute("http://10.0.2.2:9999/Bahoot/login?email="
                   + emailStr + "&password=" + passwordStr);
        }

    }

    public void toRegister (View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    private boolean validate() {

        if (emailStr.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(this, "Please enter a valid email address",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passwordStr.isEmpty()) {
            Toast.makeText(this, "Please enter your password",
                    Toast.LENGTH_SHORT).show();
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

                    InputStream stream = conn.getInputStream();
                    StringBuffer output = new StringBuffer("");
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
                    String s = "";
                    while ((s = buffer.readLine()) != null)
                        output.append(s);

                    return output.toString();
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
            if (result.contains("Wrong")) {
                Toast.makeText(getApplicationContext(),result,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Login Successful!",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userID", result);
                startActivity(intent);
                finish();


            }
        }
    }

}