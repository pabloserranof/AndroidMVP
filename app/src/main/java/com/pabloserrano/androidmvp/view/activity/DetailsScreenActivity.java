package com.pabloserrano.androidmvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pabloserrano.androidmvp.MyApplication;
import com.pabloserrano.androidmvp.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsScreenActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "IMAGE_URL";

    @BindView(R.id.imageToBeCropped) RoundedImageView roundImageView;
    @BindView(R.id.seekBar) SeekBar seekBar;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Inject
    Picasso picasso;

    private int maxRadius = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((MyApplication) this.getApplication()).getComponent().inject(this);

        Bundle bundle = getIntent().getExtras();
        String imageURL = bundle.getString(IMAGE_URL);

        picasso.load(imageURL).into(roundImageView);

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
