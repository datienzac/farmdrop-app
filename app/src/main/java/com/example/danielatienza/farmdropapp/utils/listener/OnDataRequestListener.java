package com.example.danielatienza.farmdropapp.utils.listener;

import java.util.List;

import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */
public interface OnDataRequestListener {

    void onDataError();
    void onCompleted();
    void requestData();
}