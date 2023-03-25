package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    private Button nextQuestion;
    private TextView questionNumber;
    private String userID;
    private String roomCode;
    private String selectedOption;
    private String currentQuestionID;
    private int count = 0;
    private boolean answered = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");
        roomCode = extras.getString("roomCode");

        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        nextQuestion = findViewById(R.id.nextQuestionButton);
        questionNumber = findViewById(R.id.questionNumberText);

        nextQuestion.setEnabled(false);
        Log.d("TAG","answered:" + answered);

        String currentQuestionSQL = "SELECT currentQuestionID FROM session WHERE" +
                " roomCode = '" + roomCode + "'";

        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                currentQuestionSQL);
    }

    public void option1(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=1" +
                "&userID=" + userID + "&roomCode=" + roomCode);
        selectedOption = "1";
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }
    public void option2(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=2" +
                "&userID=" + userID + "&roomCode=" + roomCode);
        selectedOption = "2";
        option1Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }
    public void option3(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=3" +
                "&userID=" + userID + "&roomCode=" + roomCode);
        selectedOption = "3";
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option4Button.setEnabled(false);
    }
    public void option4(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=4" +
                "&userID=" + userID + "&roomCode=" + roomCode);
        selectedOption = "4";
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
    }

    public void nextQuestionCheck(View v) {

        Log.d("Tag","answerrred:" + answered);

        String nextSQL = "SELECT currentQuestionID FROM session WHERE " +
                "roomCode = '" + roomCode + "'";
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                nextSQL);

    }

    private void nextQuestion(String result){

        String nextQuestionID = result.substring(18);
        Log.d("TAG","nextQuestionID:" + nextQuestionID);

        if (!nextQuestionID.matches(currentQuestionID)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userID", userID);
            intent.putExtra("roomCode", roomCode);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(this, "Please wait for the instructor to move to " +
                            "the next question",
                    Toast.LENGTH_SHORT).show();
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
            Log.d("TAG","result: " + result);
            // to retrieve the question from the server
            if (result.contains("currentQuestionID")) {
                Log.d("TAG","answeredLOL:" + answered);
                if(!answered)
                    populateQuestions(result);
                else
                    nextQuestion(result);
            }
            // to fill in question number
            else if (result.contains("qnNo"))
                setQuestionNumber(result);
            // to fill the options in the button TextView
            else if (result.contains("opt"))
                buttonQuestions(result);
            // to change button color based on result
            else if (result.contains("Question Result:"))
                buttonResponse(result);
            // disable buttons if question not retrieved
            else if (result.isEmpty())
                noQuestionSet();
        }
    }

    private void populateQuestions(String result) {

        currentQuestionID = result.substring(18);
        Log.d("Tag","currentQuestionID2:" + currentQuestionID);
        String sqlStr1 = "SELECT opt1 FROM questions WHERE id='" + currentQuestionID + "'";
        String sqlStr2 = "SELECT opt2 FROM questions WHERE id='" + currentQuestionID + "'";
        String sqlStr3 = "SELECT opt3 FROM questions WHERE id='" + currentQuestionID + "'";
        String sqlStr4 = "SELECT opt4 FROM questions WHERE id='" + currentQuestionID + "'";
        String sqlStr5 = "SELECT qnNo FROM questions WHERE id='" + currentQuestionID + "'";

        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr1);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr2);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr3);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr4);
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                sqlStr5);

    }

    private void setQuestionNumber(String result){
        String text = "Question " + result.substring(5);
        questionNumber.setText(text);
    }
    private void buttonQuestions(String result) {
        result = result.substring(5);
        if (option1Button.getText().toString().isEmpty())
            option1Button.setText(result);
        else if (option2Button.getText().toString().isEmpty())
            option2Button.setText(result);
        else if (option3Button.getText().toString().isEmpty())
            option3Button.setText(result);
        else if (option4Button.getText().toString().isEmpty())
            option4Button.setText(result);

    }

    private void buttonResponse(String result){
        Log.d("TAG",result);
        if (result.matches("Question Result:Correct")) {
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
        nextQuestion.setEnabled(true);
        answered = true;
        Log.d("TAG","answered:" + answered);
    }
    private void noQuestionSet(){
        if (count < 1) {
            option1Button.setEnabled(false);
            option2Button.setEnabled(false);
            option3Button.setEnabled(false);
            option4Button.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Error, no question found",
                    Toast.LENGTH_SHORT).show();
        }
        count++;
    }

}