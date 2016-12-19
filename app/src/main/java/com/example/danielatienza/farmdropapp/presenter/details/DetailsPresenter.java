package com.example.danielatienza.farmdropapp.presenter.details;

import android.content.Context;

import com.example.danielatienza.farmdropapp.ui.base.MvpView;

/**
 * Created by danielatienza on 14/12/2016.
 */
public interface DetailsPresenter {

    void loadProducer(long producerId);

    void attachView(MvpView view);

}