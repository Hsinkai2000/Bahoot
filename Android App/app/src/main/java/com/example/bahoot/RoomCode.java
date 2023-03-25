package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class RoomCode extends AppCompatActivity {

    private EditText roomCodeField;
    private String roomCodeStr;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_code);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");

        roomCodeField = findViewById(R.id.roomCodeField);
    }
    public void enterRoomCode(View view){
        roomCodeStr = roomCodeField.getText().toString();

        if (roomCodeStr.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please enter a room code",
                    Toast.LENGTH_SHORT).show();
        } else  {
            Log.d("Tag","Room Code: " + roomCodeStr);

            String SQL = "SELECT * FROM session WHERE " +
                    "roomCode = '"+ roomCodeStr + "'";

            new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                    SQL);
        }
    }
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
                    InputStream inputStream = conn.getInputStream();

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
            if (result.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Wrong room code, please try again",
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("roomCode",roomCodeStr);
                startActivity(intent);
                finish();
            }
        }
    }
}