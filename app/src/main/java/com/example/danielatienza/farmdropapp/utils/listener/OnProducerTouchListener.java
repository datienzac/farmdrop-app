package com.example.danielatienza.farmdropapp.utils.listener;

import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by danielatienza on 16/12/2016.
 */
public interface OnProducerTouchListener {

    void onProducerTouched(long producerId, Pair<View, String>... params);

}