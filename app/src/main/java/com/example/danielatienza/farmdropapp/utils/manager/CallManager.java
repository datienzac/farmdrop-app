package com.example.danielatienza.farmdropapp.utils.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.example.danielatienza.farmdropapp.ui.details.DetailsActivity;

/**
 * Created by danielatienza on 16/12/2016.
 */
public class CallManager {
    public static final String IMAGE = "image";

    public static final String PRODUCER_ID = "producerID";
    public static Intent detailsIntent(Context context, long producerId) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(PRODUCER_ID, producerId);
        return intent;
    }

    public void startDetailsActivity(Activity activity,
                                     long producerId, Pair<View, String>[] params) {

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, params);
        ActivityCompat.startActivity(activity, detailsIntent(activity, producerId),
                options.toBundle());

    }
}