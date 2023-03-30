package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class WaitingScreen extends AppCompatActivity {

    private String userID;
    private String roomCode;
    private String name;

    private TextView waitingText;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_screen);

        waitingText = findViewById(R.id.waiting_text);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");
        roomCode = extras.getString("room_code");
        name = extras.getString("name");

        Bundle wait = new Bundle();
        wait.putString("userID", userID);
        wait.putString("room_code", roomCode);
        wait.putString("name",name);

        Intent serviceIntent = new Intent(this, WaitingService.class);
        serviceIntent.putExtras(extras);

        startService(serviceIntent);

    }



    private BroadcastReceiver httpReceiver = new BroadcastReceiver() {
        String waitText1 = "Waiting for game to start";
        String waitText2 = "Waiting for game to start.";
        String waitText3 = "Waiting for game to start..";
        String waitText4 = "Waiting for game to start...";

        @Override
        public void onReceive(Context context, Intent intent) {
            String currentQuestionID = intent.getStringExtra("currentQuestionID");
            if (currentQuestionID != null) {
                if (!currentQuestionID.matches("0")) {
                    goToMain();
                } else {
                    if (count == 0)
                        waitingText.setText(waitText1);
                    if (count == 1)
                        waitingText.setText(waitText2);
                    if (count == 2)
                        waitingText.setText(waitText3);
                    if (count == 3)
                        waitingText.setText(waitText4);
                }

                ++count;
                if (count == 4)
                    count = 0;
            }
        }
    };

    private void goToMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("name",name);
        intent.putExtra("room_code",roomCode);
        startActivity(intent);
        Log.d("huh??",roomCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("http_request");
        registerReceiver(httpReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(httpReceiver);
        Intent serviceIntent = new Intent(this, WaitingService.class);
        stopService(serviceIntent);
    }


}