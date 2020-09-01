package com.educate.moviestreaming.adapter;

import android.widget.ImageView;

import com.educate.moviestreaming.model.Movie;

public interface MovieItemClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView); // we will need the imageview to make the shared animation between the two activity

}