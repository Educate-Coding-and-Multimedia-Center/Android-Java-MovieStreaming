package com.educate.moviestreaming.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.educate.moviestreaming.R;
import com.educate.moviestreaming.adapter.CastAdapter;
import com.educate.moviestreaming.adapter.MovieAdapter;
import com.educate.moviestreaming.model.Cast;
import com.educate.moviestreaming.model.CastList;
import com.educate.moviestreaming.model.MovieList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String BASE_POSTER_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String API_KEY = "98fcdd06fffc64d385eef4bc3bb4da22";

    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tvTitle, tvDescription;
    private FloatingActionButton playFab;
    private RecyclerView rvCast;
    private CastAdapter castAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Find Views
        playFab = findViewById(R.id.play_fab);
        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        tvTitle = findViewById(R.id.detail_movie_title);
        tvDescription = findViewById(R.id.detail_movie_desc);
        rvCast = findViewById(R.id.rv_cast);

        // Get data from intent
        String movieTitle = getIntent().getExtras().getString("title");
//        int imageResourceId = getIntent().getExtras().getInt("imgURL");
//        int imagecover = getIntent().getExtras().getInt("imgCover");
        String imageResourceId = getIntent().getExtras().getString("imgURL");
        String imagecover = getIntent().getExtras().getString("imgCover");
        String description = getIntent().getExtras().getString("description");
        int movieId = getIntent().getExtras().getInt("id", 0);

        // Pass data to views
        Glide.with(this).load(BASE_POSTER_IMAGE_URL +  imageResourceId).into(MovieThumbnailImg);
        Glide.with(this).load(BASE_POSTER_IMAGE_URL + imagecover).into(MovieCoverImg);
        tvTitle.setText(movieTitle);
        tvDescription.setText(description);
        getSupportActionBar().setTitle(movieTitle);

//         setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        playFab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));


        // SETUP CAST
//        List<Cast> mData = new ArrayList<>();
//        mData.add(new Cast("Person", R.drawable.poster1));
//        mData.add(new Cast("Person", R.drawable.poster2));
//        mData.add(new Cast("Person", R.drawable.poster3));
//        mData.add(new Cast("Person", R.drawable.poster4));
//        mData.add(new Cast("Person", R.drawable.poster5));

//        castAdapter = new CastAdapter(this, mData);
//        rvCast.setAdapter(castAdapter);
//        rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Log.d("movie id : ", "" + movieId);
        InitCast(movieId);

        // SET INTENT TO MOVIE PLAYER
        playFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailActivity.this, MoviePlayerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InitCast(int movieId) {
        String URL = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + API_KEY;
        List<Cast> data = new ArrayList<>();
        final CastAdapter castAdapter = new CastAdapter(this, data);
        rvCast.setAdapter(castAdapter);
        rvCast.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                CastList listData = gson.fromJson(response, CastList.class);

                if (listData.cast != null){
                    castAdapter.SetData(listData.cast);
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
