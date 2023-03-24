package com.example.bahoot;

import static java.lang.Thread.sleep;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SQL {
    private String sqlStr;
    private String resultStr;


    public void setStatement(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public void executeQuery() {
        Log.d("TAG", "before: " + this.resultStr);
        new SQLTask().execute("http://10.0.2.2:9999/Bahoot/SQL?sql="
                + sqlStr);
    }

    public String getResults(){
        Log.d("TAG", "getResults(): " + this.resultStr);
        return this.resultStr;
    }


    // Run the HTTP request in a background thread, separating from the main UI thread
    public class SQLTask extends AsyncTask<String, Void, String> {
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
            Log.d("TAG", "during: " + result);
            SQL.this.resultStr = result;
            Log.d("TAG", "duringSt: " + SQL.this.resultStr);
        }
    }

}

