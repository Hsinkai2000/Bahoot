package com.example.bahoot;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;
import java.net.URL;

public class WaitingService extends Service {
    private Timer timer;
    private String userID;
    private String roomCode;
    private String name;


    private static final int INTERVAL = 1000; // 1 second

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            userID = extras.getString("userID");
            roomCode = extras.getString("room_code");
            name = extras.getString("name");

            // Do something with the data
        }

        startTimer();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendHttpRequest();
            }
        }, 0, INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void sendHttpRequest() {
        URL url = null;
        HttpURLConnection conn = null;
        try {
            String currentQuestionSQL = "SELECT current_question_id FROM session WHERE" +
                    " room_code = '" + roomCode + "'";

            url = new URL("http://192.168.1.11:9999/Bahoot/SQL?sql=" +
                    currentQuestionSQL);

            conn = (HttpURLConnection) url.openConnection();


            int statusCode = conn.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                String currentQuestionID = conn.getHeaderField("current_question_id");
                Intent intent = new Intent("http_request");
                intent.putExtra("currentQuestionID", currentQuestionID);
                sendBroadcast(intent);
                Log.d("Still working", "still working!!");


            } else {
                // Handle the error
            }
        } catch (IOException e) {
            // Handle the error
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }



}
