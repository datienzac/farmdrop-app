package com.example.danielatienza.farmdropapp.presenter.main;

import com.example.danielatienza.farmdropapp.ui.base.MvpView;

/**
 * Created by danielatienza on 14/12/2016.
 */

public interface MainPresenter {
    void loadProducers();

    void loadNextPage();

    void attachView(MvpView view);

    void onRefresh();

    boolean onSearch(String search);

    void onSearchClosed();

    void onSearchShown();
}
