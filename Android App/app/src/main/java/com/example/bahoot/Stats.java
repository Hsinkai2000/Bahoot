package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Stats extends AppCompatActivity {


    TextView tvScore;

    private String userID;
    private String name;
    private String score;
    private String roomCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");
        roomCode = extras.getString("room_code");
        name = extras.getString("name");
        Log.d("Room Code Stats", roomCode);

        tvScore = findViewById(R.id.scoreText);
        HttpTask httpTask = new HttpTask();
        httpTask.setGetScore(true);
        httpTask.execute("http://10.91.253.112:9999/Bahoot/getScore?userID="
                +userID+"&room_code="+roomCode); // Send HTTP request
    }

    public void home(View v) {
        Intent intent = new Intent(getApplicationContext(), RoomCode.class);
        intent.putExtra("userID", userID);
        intent.putExtra("room_code", roomCode);
        intent.putExtra("name",name);
        finish();
        startActivity(intent);
    }

    private class HttpTask extends AsyncTask<String, Void, String> {
        private boolean getScore;

        public void setGetScore(boolean getScore) {
            this.getScore = getScore;
        }

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

                    if (getScore)
                        score = conn.getHeaderField("score");


                    return null;
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
            tvScore.setText(score);  // put it on TextView
        }
    }
}

