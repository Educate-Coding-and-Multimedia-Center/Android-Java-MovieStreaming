package com.educate.moviestreaming.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.educate.moviestreaming.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MoviePlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    public static final String VIDEO_TEST_URL = "http://techslides.com/demos/sample-videos/small.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreen();
        hideActionBar();
        setContentView(R.layout.activity_movie_player);
        initExoPlayer();
    }

    private void hideActionBar() {
        getSupportActionBar().hide();
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initExoPlayer() {
        playerView = findViewById(R.id.movie_exo_player);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, "appname")
        );
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(VIDEO_TEST_URL));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}
