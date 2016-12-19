package com.example.danielatienza.farmdropapp.ui.main;

import com.example.danielatienza.farmdropapp.ui.base.MvpView;
import java.util.List;
import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */

public interface MainView extends MvpView {

    void showProgress();
    void hideProgress();
    void onError();
    void showProducers(List<ProducerData> producers);
    void resetAdapter();
    int getItemsCount();
}
