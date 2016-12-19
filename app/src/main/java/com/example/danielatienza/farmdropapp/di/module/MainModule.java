package com.example.danielatienza.farmdropapp.di.module;

import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.presenter.main.MainPresenter;
import com.example.danielatienza.farmdropapp.presenter.main.MainPresenterImpl;
import com.example.danielatienza.farmdropapp.ui.main.MainView;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Module
public class MainModule {
    private MainView mMainView;

    public MainModule(MainView mainView) {
        this.mMainView = mainView;
    }

    @Provides
    public MainView provideMainView() {
        return mMainView;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView mainView,
                                              ProducersRepository producersRepository,
                                              ProducersManager producersManager) {
        return new MainPresenterImpl(mainView,
                producersRepository,
                producersManager);
    }
}