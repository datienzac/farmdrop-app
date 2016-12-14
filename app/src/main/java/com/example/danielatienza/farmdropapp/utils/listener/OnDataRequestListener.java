package com.example.danielatienza.farmdropapp.utils.listener;

/**
 * Created by danielatienza on 14/12/2016.
 */
public interface OnDataRequestListener {

    void onDataError();
    void requestData();
    void onCompleted();

}