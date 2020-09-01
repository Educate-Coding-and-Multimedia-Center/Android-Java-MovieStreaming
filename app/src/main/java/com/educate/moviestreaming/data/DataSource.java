package com.educate.moviestreaming.data;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.educate.moviestreaming.R;
import com.educate.moviestreaming.model.Movie;
import com.educate.moviestreaming.model.Slide;
import com.educate.moviestreaming.model.SlideList;
import com.educate.moviestreaming.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final String API_KEY = "98fcdd06fffc64d385eef4bc3bb4da22";
    private static final String BASE_POSTER_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    private SlideList listData;

    public List<Slide> getSlideMovies(Context context) {
        String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";

        final List<Slide> slideData = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                listData = gson.fromJson(response, SlideList.class);

                if (listData.results != null) {
                    slideData.addAll(listData.results);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return slideData;
    }

    public static List<Movie> getWeekMovies() {

        List<Movie> lstMovies = new ArrayList<>();
//        lstMovies.add(new Movie("Moana", R.drawable.moana,R.drawable.spidercover));
//        lstMovies.add(new Movie("Black P",R.drawable.blackp,R.drawable.spidercover));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));

        return lstMovies;
    }

    public static List<Movie> getPopularMovies() {
        List<Movie> lstMovies = new ArrayList<>();
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
//        lstMovies.add(new Movie("Black P",R.drawable.blackp,R.drawable.spidercover));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
//        lstMovies.add(new Movie("Moana", R.drawable.moana,R.drawable.spidercover));
//        lstMovies.add(new Movie("The Martian",R.drawable.themartian));

        return lstMovies;
    }
}
