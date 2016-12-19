package com.example.danielatienza.farmdropapp.ui.details;

import com.example.danielatienza.farmdropapp.ui.base.MvpView;

import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */
public interface DetailsView extends MvpView {

    void showProgress();
    void hideProgress();
    void onError();
    void showProducer(ProducerData producerData);

}