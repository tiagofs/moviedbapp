package com.themoviedbapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.themoviedbapp.Constants;
import com.themoviedbapp.dataModels.Movie;
import com.themoviedbapp.R;

import java.util.List;

/**
 * Created by Tiago on 11-09-2015.
 */
public class MoviesNowPlayingAdapter extends ArrayAdapter<Movie> {

    private final Context context;
    private final List<Movie> movieMoviesInfo;

    public MoviesNowPlayingAdapter(Context context, List<Movie> movieMoviesInfoList) {
        super(context, R.layout.item_movie, movieMoviesInfoList);
        this.movieMoviesInfo = movieMoviesInfoList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = this.movieMoviesInfo.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.text_view_title);
        tvTitle.setText(movie.getTitle());

        TextView tvCriticScore = (TextView) convertView.findViewById(R.id.text_view_criticScore);
        tvCriticScore.setText(movie.getVoteAverage().toString());

        TextView tvOverview = (TextView) convertView.findViewById(R.id.text_view_overview);
        tvOverview.setText(movie.getOverview());

        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.image_view_posterImage);
        Picasso.with(getContext()).load(Constants.POSTER150 + movie.getPosterPath()).
                placeholder(R.drawable.small_movie_poster).
                into(ivPosterImage);

        return convertView;
    }
}
