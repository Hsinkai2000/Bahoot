package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;

    private TextView nameText;
    private String userID;

    private String nameStr = "";
    private String sqlStr1;
    private String sqlStr2;
    private String sqlStr3;
    private String sqlStr4;

    private String selectedOption;

    private SQL sql = new SQL();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");
        /*
        sqlStr = "SELECT name FROM users WHERE id='" + userID + "'";
        Log.d("TAG", "userID: " + userID);
        sql.setStatement(sqlStr);
        sql.executeQuery();
        nameStr = sql.getResults();
        Log.d("TAG", "name: " + nameStr);
        nameText = findViewById(R.id.mainTextName);
        nameText.setText(nameStr);*/

        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);

        sqlStr1 = "SELECT opt1 FROM questions WHERE current='Y'";
        sqlStr2 = "SELECT opt2 FROM questions WHERE current='Y'";
        sqlStr3 = "SELECT opt3 FROM questions WHERE current='Y'";
        sqlStr4 = "SELECT opt4 FROM questions WHERE current='Y'";

        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr1);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr2);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr3);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr4);

    }

    public void option1(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=1" +
                "&userID=" + userID);
        selectedOption = "1";
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }
    public void option2(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=2" +
                "&userID=" + userID);
        selectedOption = "2";
        option1Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }
    public void option3(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=3" +
                "&userID=" + userID);
        selectedOption = "3";
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option4Button.setEnabled(false);
    }
    public void option4(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=4" +
                "&userID=" + userID);
        selectedOption = "4";
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
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
            if (!result.matches("Correct") && !result.matches("Incorrect")) {
                if (option1Button.getText().toString().isEmpty())
                    option1Button.setText(result);
                else if (option2Button.getText().toString().isEmpty())
                    option2Button.setText(result);
                else if (option3Button.getText().toString().isEmpty())
                    option3Button.setText(result);
                else if (option4Button.getText().toString().isEmpty())
                    option4Button.setText(result);
            }
            else {
                Log.d("TAG",result);
                if (result.matches("Correct")) {
                    if (selectedOption.matches("1"))
                        option1Button.setBackgroundColor(Color.GREEN);
                    else if (selectedOption.matches("2"))
                        option2Button.setBackgroundColor(Color.GREEN);
                    else if (selectedOption.matches("3"))
                        option3Button.setBackgroundColor(Color.GREEN);
                    else if (selectedOption.matches("4"))
                        option4Button.setBackgroundColor(Color.GREEN);
                }
                else {
                    if (selectedOption.matches("1"))
                        option1Button.setBackgroundColor(Color.RED);
                    else if (selectedOption.matches("2"))
                        option2Button.setBackgroundColor(Color.RED);
                    else if (selectedOption.matches("3"))
                        option3Button.setBackgroundColor(Color.RED);
                    else if (selectedOption.matches("4"))
                        option4Button.setBackgroundColor(Color.RED);
                }
            }
        }
    }
}