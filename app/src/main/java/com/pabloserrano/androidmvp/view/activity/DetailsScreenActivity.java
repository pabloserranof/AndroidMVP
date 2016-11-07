package com.pabloserrano.androidmvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pabloserrano.androidmvp.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DetailsScreenActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "IMAGE_URL";

    @Inject
    Picasso picasso;

    private RoundedImageView roundImageView;
    private SeekBar seekBar;

    private int maxRadius = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();

        String imageURL = bundle.getString(IMAGE_URL);

        roundImageView = (RoundedImageView) findViewById(R.id.imageToBeCropped);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        picasso.with(this).load(imageURL).into(roundImageView);

        ViewTreeObserver viewTreeObserver = roundImageView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    roundImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int viewWidth = roundImageView.getWidth();
                    maxRadius = viewWidth / 2;
                }
            });
        }

        initListeners();
    }

    private void initListeners() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                roundImageView.setCornerRadius((((float) i) / 100) * maxRadius);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public static void startActivity(Context context, String imageURL) {
        Intent intent = new Intent(context, DetailsScreenActivity.class);
        intent.putExtra(DetailsScreenActivity.IMAGE_URL, imageURL);

        context.startActivity(intent);
    }
}
