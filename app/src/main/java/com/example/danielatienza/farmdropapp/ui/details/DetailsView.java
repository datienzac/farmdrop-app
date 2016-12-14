package com.example.danielatienza.farmdropapp.ui.details;

/**
 * Created by danielatienza on 14/12/2016.
 */
public interface DetailsView {

    void showProgress();
    void hideProgress();
    void changeStatusBarColor(int color);
    void onError();
//    void loadImageData(ImageData imageData);
//    void loadContributorsData(String contributor);

}