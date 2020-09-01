package com.educate.moviestreaming.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.educate.moviestreaming.R;
import com.educate.moviestreaming.adapter.MovieAdapter;
import com.educate.moviestreaming.adapter.MovieItemClickListener;
import com.educate.moviestreaming.adapter.SliderPagerAdapter;
import com.educate.moviestreaming.data.DataSource;
import com.educate.moviestreaming.model.Movie;
import com.educate.moviestreaming.model.MovieList;
import com.educate.moviestreaming.model.Slide;
import com.educate.moviestreaming.model.SlideList;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

    private static final String API_KEY = "98fcdd06fffc64d385eef4bc3bb4da22";

    private List<Slide> lstSlides;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView MoviesRV, PopularRV ;

    private SliderPagerAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderpager = findViewById(R.id.slider_pager) ;
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);
        PopularRV = findViewById(R.id.Rv_popular);

        // prepare a list of slides ..
        lstSlides = new ArrayList<>() ;
//        lstSlides.add(new Slide(R.drawable.slide1,"Slide Title \nmore text here"));
//        lstSlides.add(new Slide(R.drawable.slide2,"Slide Title \nmore text here"));
//        lstSlides.add(new Slide(R.drawable.slide1,"Slide Title \nmore text here"));
//        lstSlides.add(new Slide(R.drawable.slide2,"Slide Title \nmore text here"));
        sliderAdapter = new SliderPagerAdapter(this,lstSlides);
        sliderpager.setAdapter(sliderAdapter);
        getSlideMovies();

        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderpager,true);

        // Recyclerview Setup
        // ini data
        InitWeekMovie();
        InitPopularMovie();
    }

    private void InitWeekMovie() {
        String URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY +"&language=en-US&page=1";

        List<Movie> data = new ArrayList<>();
        final MovieAdapter movieAdapter = new MovieAdapter(this, data,this);
//        MovieAdapter movieAdapter = new MovieAdapter(this,lstMovies);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MovieList listData = gson.fromJson(response, MovieList.class);

                if (listData.results != null){
                    movieAdapter.SetData(listData.results);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response error : ", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void InitPopularMovie() {
        String URL = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY +"&language=en-US&page=1";

        List<Movie> data = new ArrayList<>();
        final MovieAdapter movieAdapter = new MovieAdapter(this, data,this);
//        MovieAdapter movieAdapter = new MovieAdapter(this,lstMovies);
        PopularRV.setAdapter(movieAdapter);
        PopularRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MovieList listData = gson.fromJson(response, MovieList.class);

                if (listData.results != null){
                    movieAdapter.SetData(listData.results);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response error : ", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    class SliderTimer extends TimerTask {
        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<lstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });
        }
    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        // here we send movie information to detail activity
        // also we ll create the transition animation between the two activity

        Intent intent = new Intent(this,MovieDetailActivity.class);
        // send movie information to detailActivity
        intent.putExtra("id", movie.getId());
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgCover",movie.getCoverPhoto());
        intent.putExtra("description", movie.getDescription());

        // lets create the animation
        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                    movieImageView,"sharedName");
        }
        startActivity(intent,options.toBundle());
        // i l make a simple test to see if the click works

        Toast.makeText(this,"item clicked : " + movie.getTitle(),Toast.LENGTH_LONG).show();
        // it works great

    }

    public void getSlideMovies() {
        String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                SlideList listData = gson.fromJson(response, SlideList.class);

                if (listData.results != null){
                    sliderAdapter.SetData(listData.results);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response error : ", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
