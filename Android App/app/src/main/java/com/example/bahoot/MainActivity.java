package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
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
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private Button nextQuestion;
    private Button commentButton;
    private TextView questionNumber;
    private String userID;
    private String roomCode;
    private String selectedOption;
    private String correctOption;
    private String currentQuestionID;
    private String currentQuestionNumber;
    private String nextQuestionCheck;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionResult;
    private String responseTableID;
    private String userComment;
    private String sqlUpdateCheck;
    private int count = 0;
    private boolean answered = false;
    private boolean commenting = false;


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
        commentButton = findViewById(R.id.commentButton);

        nextQuestion.setEnabled(false);
        Log.d("TAG","answered:" + answered);

        getCurrentQuestionID();

    }

    public void option1(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=1" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment);
        selectedOption = "1";

    }
    public void option2(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=2" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment);
        selectedOption = "2";
    }
    public void option3(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=3" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment);
        selectedOption = "3";
    }
    public void option4(View v) {
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/response?option=4" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment);
        selectedOption = "4";
    }

    private void getCurrentQuestionID(){
        String currentQuestionSQL = "SELECT currentQuestionID FROM session WHERE" +
                " roomCode = '" + roomCode + "'";

        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                currentQuestionSQL);
    }


    public void nextQuestionButton(View v) {

        Log.d("Tag", "answerrred:" + answered);

        String nextSQL = "SELECT currentQuestionID FROM session WHERE " +
                "roomCode = '" + roomCode + "'";
        new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                nextSQL);

    }

    private void nextQuestion(){

        if (!nextQuestionCheck.matches(currentQuestionID)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userID", userID);
            intent.putExtra("roomCode", roomCode);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);

        } else {
            Toast.makeText(getApplicationContext(), "Please wait for the instructor " +
                            "to move to the next question",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void comment(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        String commentText;
        if (userComment == null) {
            alert.setTitle("Comment");
            commentText = "Comment";
        }
        else {
            alert.setTitle("Edit Comment");
            commentText = "Update Comment";
        }

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setHint("Enter Comment");
        input.setHeight(500);
        input.setText(userComment);

        int maxLength = 500;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        input.setFilters(fArray);

        alert.setView(input);

        alert.setPositiveButton(commentText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userComment = input.getText().toString();
                if (answered) {
                    String updateSQL = "UPDATE responses SET comment='" + userComment
                            + "' WHERE id='" + responseTableID +"'";
                    new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                            updateSQL);
                }
                try{
                    if (userComment.isEmpty())
                        userComment = null;
                }catch (Exception e){

                }

                if (userComment != null)
                    commentButton.setText("Edit Comment");
                else
                    commentButton.setText("Comment");
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();

    }

    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
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

                    if (currentQuestionID == null) {
                        currentQuestionID = conn.getHeaderField("currentQuestionID");
                        populateQuestions(1);
                    }

                    else if (currentQuestionNumber == null){
                        currentQuestionNumber = conn.getHeaderField("qnNo");
                        option1 = conn.getHeaderField("opt1");
                        option2 = conn.getHeaderField("opt2");
                        option3 = conn.getHeaderField("opt3");
                        option4 = conn.getHeaderField("opt4");
                        correctOption = conn.getHeaderField("correctOpt");
                        populateQuestions(2);
                    }
                    else if (questionResult == null) {
                        questionResult = conn.getHeaderField("Result");
                        responseTableID = conn.getHeaderField("Response-Table-ID");
                    }
                    else if (answered) {
                        nextQuestionCheck = conn.getHeaderField("currentQuestionID");
                        sqlUpdateCheck = conn.getHeaderField("SQL");
                        if (sqlUpdateCheck != null)
                            if (sqlUpdateCheck.matches("Update"))
                                commenting = true;
                    }
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

            // disable buttons if question not retrieved
            if (currentQuestionID == null)
                noQuestionSet();
            // show results
            else if (questionResult != null && !answered)
                buttonResponse(questionResult);
            // move to next question if answered already
            else if (answered && !commenting)
                nextQuestion();

            commenting = false;
            sqlUpdateCheck = null;

        }
    }

    private void populateQuestions(int step) {

        if (step == 1) {
            Log.d("Tag", "currentQuestionID:" + currentQuestionID);
            String sqlStr = "SELECT * FROM questions WHERE id='" + currentQuestionID + "'";

            new HttpTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                    sqlStr);
        }
        else if (step == 2) {
            questionNumber.setText("Question " + currentQuestionNumber);
            option1Button.setText(option1);
            option2Button.setText(option2);
            option3Button.setText(option3);
            option4Button.setText(option4);
        }

    }

    private void buttonResponse(String result){
        Log.d("TAG",result);

        if (correctOption.matches("1"))
            option1Button.setTextColor(Color.GREEN);
        else if (correctOption.matches("2"))
            option2Button.setTextColor(Color.GREEN);
        else if (correctOption.matches("3"))
            option3Button.setTextColor(Color.GREEN);
        else if (correctOption.matches("4"))
            option4Button.setTextColor(Color.GREEN);

        if (result.matches("Incorrect")) {
            if (selectedOption.matches("1"))
                option1Button.setTextColor(Color.RED);
            else if (selectedOption.matches("2"))
                option2Button.setTextColor(Color.RED);
            else if (selectedOption.matches("3"))
                option3Button.setTextColor(Color.RED);
            else if (selectedOption.matches("4"))
                option4Button.setTextColor(Color.RED);
        }

        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
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