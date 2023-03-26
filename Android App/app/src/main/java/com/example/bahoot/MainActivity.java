package com.example.bahoot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private Button nextQuestion;
    private Button commentButton;
    private TextView questionNumber;
    private TextView scoreTextView;
    private String userID;
    private String name;
    private String roomCode;
    private String selectedOption;
    private String correctOption;
    private String currentQuestionID;
    private String currentQuestionNumber;
    private String questionSetID;
    private String nextQuestionCheck;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionResult;
    private String responseTableID;
    private String userComment;
    private String responseTableIDCheck;
    private String choiceCheck;
    private String resultCheck;
    private String scoreStr;
    private int count = 0;
    private int score;
    private boolean answered = false;
    private boolean resume = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");
        roomCode = extras.getString("roomCode");
        name = extras.getString("name");

        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        nextQuestion = findViewById(R.id.nextQuestionButton);
        questionNumber = findViewById(R.id.questionNumberText);
        commentButton = findViewById(R.id.commentButton);
        scoreTextView = findViewById(R.id.scoreText);

        // Disables the next question button at the start
        nextQuestion.setEnabled(false);
        Log.d("TAG","answered:" + answered);
        // Gets the current question in the room and fills in the questions on screen
        getCurrentQuestionID();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (answered)
         nextQuestionButton();
    }


    /*--------------------------------Buttons-----------------------------------------------------*/
    public void logOut(View v) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    // Function for option 1 button
    public void option1(View v) {
        HttpTask http = new HttpTask();
        http.setGetResult(true);
        http.execute("http://10.0.2.2:9999/Bahoot/response?option=1" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment
                + "&qnSetID=" + questionSetID);
        selectedOption = "1";

    }

    // Function for option 2 button
    public void option2(View v) {
        HttpTask http = new HttpTask();
        http.setGetResult(true);
        http.execute("http://10.0.2.2:9999/Bahoot/response?option=2" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment
                + "&qnSetID=" + questionSetID);
        selectedOption = "2";
    }

    // Function for option 3 button
    public void option3(View v) {
        HttpTask http = new HttpTask();
        http.setGetResult(true);
        http.execute("http://10.0.2.2:9999/Bahoot/response?option=3" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment
                + "&qnSetID=" + questionSetID);
        selectedOption = "3";
    }

    // Function for option 4 button
    public void option4(View v) {
        HttpTask http = new HttpTask();
        http.setGetResult(true);
        http.execute("http://10.0.2.2:9999/Bahoot/response?option=4" +
                "&userID=" + userID + "&roomCode=" + roomCode + "&userComment=" + userComment
                + "&qnSetID=" + questionSetID);
        selectedOption = "4";
    }

    // Function for next question button
    public void nextQuestionButton(View v) {

        Log.d("Tag", "answerrred:" + answered);

        String nextSQL = "SELECT currentQuestionID FROM session WHERE " +
                "roomCode = '" + roomCode + "'";
        HttpTask httpTask = new HttpTask();
        httpTask.setDoNextQuestion(true);
        httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                nextSQL);

    }

    /*
     * Function for comment/edit comment button
     * Creates a dialog box for the user to enter comments
     * Sends the comment/ updated comment to the servlet
     */
    public void comment(View v) {

        String tableID ="";

        if (responseTableID != null)
            tableID = responseTableID;
        else if (responseTableIDCheck != null)
            tableID = responseTableIDCheck;

        String finalTableID = tableID;

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

        alert.setPositiveButton(commentText, (dialog, whichButton) -> {
            userComment = input.getText().toString();

            if (answered) {
                String updateSQL = "UPDATE responses SET comment='" + userComment
                        + "' WHERE id='" + finalTableID +"'";

                HttpTask httpTask = new HttpTask();
                httpTask.setCommenting(true);
                httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                        updateSQL);
            }

            if (userComment.isEmpty())
                userComment = null;

            if (userComment != null)
                commentButton.setText(R.string.edit_comment);
            else
                commentButton.setText(R.string.comment);
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {

        });
        alert.show();
    }

    /*--------------------------------Methods-----------------------------------------------------*/

    // Gets the roomCode's currentQuestionID column value from the session table
    private void getCurrentQuestionID() {
        String currentQuestionSQL = "SELECT currentQuestionID FROM session WHERE" +
                " roomCode = '" + roomCode + "'";
        HttpTask httpTask = new HttpTask();
        httpTask.setGetCurrentQuestionID(true);
        httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                currentQuestionSQL);
    }

    // Called when onResume() is called, update the screen upon refreshing
    private void nextQuestionButton() {

        Log.d("Tag", "answerrred:" + answered);

        String nextSQL = "SELECT currentQuestionID FROM session WHERE " +
                "roomCode = '" + roomCode + "'";
        HttpTask httpTask = new HttpTask();
        httpTask.setDoNextQuestion(true);
        httpTask.setResuming(true);
        httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                nextSQL);
    }

    /*
     * Check if the next question has been set by comparing it with the current question
     * If set, move to next question
     * Else stay
     */
    private void nextQuestion() {

        if (!nextQuestionCheck.matches(currentQuestionID)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("userID", userID);
            intent.putExtra("roomCode", roomCode);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);

        } else if (!resume){
            Toast.makeText(getApplicationContext(), "Please wait for the instructor " +
                            "to move to the next question",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Fills in the current question number and options
    private void populateQuestions(int step) {

        // Sends a SQL query to the servlet to get the current question
        if (step == 1) {
            Log.d("Tag", "currentQuestionID:" + currentQuestionID);
            String sqlStr = "SELECT * FROM questions WHERE id='" + currentQuestionID + "'";

            HttpTask httpTask = new HttpTask();
            httpTask.setGetQuestion(true);
            httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                    sqlStr);

        }

        // Fills in the option's text box with the options
        else if (step == 2) {
            String text = "Question " + currentQuestionNumber;
            questionNumber.setText(text);
            option1Button.setText(option1);
            option2Button.setText(option2);
            option3Button.setText(option3);
            option4Button.setText(option4);
        }

    }

    // Gets the current score from DB
    // Creates a new entry if score not found
    // Fills the score text box
    private void getScore(int step) {

        String scoreCheckSQL = "SELECT * FROM score WHERE " +
                "userID='" + userID + "' AND roomCode='" + roomCode + "'";
        HttpTask httpTask = new HttpTask();

        if (step == 1) {
            httpTask.setScoreCheck(true);
            httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                    scoreCheckSQL);

        } else if (step == 2) {
            httpTask.setScoreCheck(false);
            score = 0;
            scoreTextView.setText(R.string.zero_score);
            scoreCheckSQL = "INSERT INTO score (roomCode, userID, name, score) " +
                    "VALUES ('" +roomCode +"', '" + userID + "', '"
                    + name + "', '" + score + "')" ;
            httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                    scoreCheckSQL);

        }  else if (step == 3) {
            String text = "Score: " + scoreStr;
            scoreTextView.setText(text);
        }

    }

    // Checks if the user has already answered the question from previous attempts
    private void answeredCheck(int step) {
        String result;

        if (step == 1) {
            String sqlCheck = "SELECT * FROM responses WHERE "
                    + "questionNo='" + currentQuestionNumber
                    + "' AND userID='" + userID + "'";
            HttpTask httpTask = new HttpTask();
            httpTask.setAnsweredCheck(true);
            httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                    sqlCheck);

        } else if (step == 2) {
            selectedOption = choiceCheck;
            buttonResponse(resultCheck,true);
            Toast.makeText(getApplicationContext(), "You've already answered this question.\n" +
                            "Please wait for the next question.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Triggers when the app receives the question results from the servlet
     * Makes the correct answer show in green
     * If the selected option is incorrect, makes it red
     */
    private void buttonResponse(String result, boolean answerCheck) {
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

        if (!answerCheck) {
            if (result.matches("Correct")) {
                incrementScore();
            }
        }
    }

    // Triggers only if no questions are received from the servlet
    private void noQuestionSet() {
        if (count < 1) {
            option1Button.setEnabled(false);
            option2Button.setEnabled(false);
            option3Button.setEnabled(false);
            option4Button.setEnabled(false);
            commentButton.setEnabled(false);
            nextQuestion.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Error, no question found",
                    Toast.LENGTH_SHORT).show();
        }
        count++;
    }

    private void incrementScore(){
        ++score;
        String scoreIncSQL = "UPDATE SCORE SET score='" + score + "' " +
                "WHERE userID='" + userID + "' AND roomCode='" + roomCode + "'";
        HttpTask httpTask = new HttpTask();
        httpTask.execute("http://10.0.2.2:9999/Bahoot/SQL?sql=" +
                scoreIncSQL);
        String text = "Score: " + String.valueOf(score);
        scoreTextView.setText(text);
    }

    // Responsible for establishing and sending HTTP requests to the servlet
    private class HttpTask extends AsyncTask<String, Void, String> {

        /*---------------Boolean Flags------------------------------*/
        /* Changes the checking algorithm depending on the flag set */
        private boolean getCurrentQuestionID = false;
        private boolean getQuestion = false;
        private boolean answeredCheck = false;
        private boolean getResult = false;
        private boolean commenting = false;
        private boolean doNextQuestion = false;
        private boolean resuming = false;
        private boolean scoreCheck = false;

        /*---------------Boolean Flags Setters-----------------*/

        public void setGetCurrentQuestionID(boolean bool){
            this.getCurrentQuestionID = bool;
        }
        public void setGetQuestion(boolean bool){
            this.getQuestion = bool;
        }

        public void setAnsweredCheck(boolean bool){
            this.answeredCheck = bool;
        }
        public void setGetResult(boolean bool){
            this.getResult = bool;
        }
        public void setCommenting(boolean bool){
            this.commenting= bool;
        }

        public void setDoNextQuestion(boolean bool){
            this.doNextQuestion = bool;
        }
        public void setResuming(boolean bool){
            this.resuming = bool;
        }
        public void setScoreCheck(boolean bool){
            this.scoreCheck = bool;
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

                    /* ----------- Checking Algorithm--------------------------*/
                    /* Based on the flags above, gets variables from HTTP headers
                     * and sets the appropriate variable values
                     */

                    if (this.getCurrentQuestionID) {
                        currentQuestionID = conn.getHeaderField("currentQuestionID");
                        populateQuestions(1);
                    }

                    else if (this.getQuestion){

                        questionSetID = conn.getHeaderField("setID");
                        currentQuestionNumber = conn.getHeaderField("qnNo");
                        option1 = conn.getHeaderField("opt1");
                        option2 = conn.getHeaderField("opt2");
                        option3 = conn.getHeaderField("opt3");
                        option4 = conn.getHeaderField("opt4");
                        correctOption = conn.getHeaderField("correctOpt");
                        populateQuestions(2);
                        getScore(1);
                        answeredCheck(1);
                        Log.d("setID",questionSetID);
                    }

                    if (this.answeredCheck) {
                        responseTableIDCheck = conn.getHeaderField("id");
                        choiceCheck = conn.getHeaderField("choice");
                        resultCheck = conn.getHeaderField("result");
                    }

                    else if (this.getResult) {
                        questionResult = conn.getHeaderField("Result");
                        responseTableID = conn.getHeaderField("Response-Table-ID");
                    }

                    else if (this.doNextQuestion) {
                        nextQuestionCheck = conn.getHeaderField("currentQuestionID");
                    }

                    else if (this.scoreCheck) {
                        scoreStr = conn.getHeaderField("score");
                        if (scoreStr == null)
                            getScore(2);
                        else {
                            score = Integer.parseInt(scoreStr);
                            getScore(3);
                        }
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

            // disable buttons if question is not retrieved
            if (currentQuestionID == null) {
                noQuestionSet();
                return;
            }
            // disable buttons if user has already answered the question in previous attempts
            if (this.answeredCheck && responseTableIDCheck != null) {
                answeredCheck(2);
                return;
            }
            // show results
            if (questionResult != null && !answered) {
                buttonResponse(questionResult,false);
                return;
            }
            // move to next question
            if (doNextQuestion) {
                if (this.resuming)
                    resume = true;
                nextQuestion();
                resume = false;
            }
        }
    }
}