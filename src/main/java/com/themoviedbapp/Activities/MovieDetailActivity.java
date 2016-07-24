package com.themoviedbapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.themoviedbapp.Constants;
import com.themoviedbapp.dataModels.Casts;
import com.themoviedbapp.dataModels.Movie;
import com.themoviedbapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tiago on 21-07-2016.
 */
public class MovieDetailActivity extends AppCompatActivity{

    private static final String TAG="MovieDetailActivity";
    private Context context;
    private Toolbar toolbar;
    private Casts casts;
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvAverageScore;
    private TextView tvCast;
    private TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setToolbar();
        context = this;
        fetchViews();
        //Populate the data from the Movie into our views
        Movie movie = getIntent().getExtras().getParcelable("movie");
        loadMovie(movie);
    }

    private void fetchViews() {
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvAverageScore = (TextView) findViewById(R.id.tvAverageScore);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
    }

    private void setToolbar() {
        this.toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadMovie(Movie movie) {
        if(isOnline()){
            MoviesProcessor myTask = new MoviesProcessor();
            myTask.execute(Constants.RELATIVE_URL + Constants.MOVIE + movie.getId() + Constants.CASTS +Constants.API_KEY);
        }
        else{
            tvCast.setText("(Turn on the Internet to see cast)");
            Toast.makeText(this, "You are Offline! Please turn on your network.", Toast.LENGTH_LONG).show();
        }

        Picasso.with(context).load(Constants.POSTER185 + movie.getPosterPath()).
                placeholder(R.drawable.small_movie_poster).
                into(ivPosterImage);
        tvTitle.setText(movie.getTitle());
        tvYear.setText(movie.getYear());
        tvAverageScore.setText(Html.fromHtml("<b>Average Score:</b> " + movie.getVoteAverage()));
        tvOverview.setText(movie.getOverview());
    }

    private boolean isOnline(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    private class MoviesProcessor extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context, "Wait!", "Downloading movie information");
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            Integer result = 0;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    String response = convertInputStreamToString(inputStream);
                    processMovieCasts(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            }catch (Exception e){
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if(result == 1){
                tvCast.setText(casts.getCastName());
            }

            progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            progressDialog.dismiss();
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }
            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }
        return result;
    }

    private void processMovieCasts(String infoString){

        this.casts = new Casts();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        this.casts = gson.fromJson(infoString, Casts.class);
    }

}
