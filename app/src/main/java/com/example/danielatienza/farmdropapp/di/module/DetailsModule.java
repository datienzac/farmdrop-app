package com.example.danielatienza.farmdropapp.di.module;

import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.presenter.details.DetailsPresenter;
import com.example.danielatienza.farmdropapp.presenter.details.DetailsPresenterImpl;
import com.example.danielatienza.farmdropapp.ui.details.DetailsView;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Module
public class DetailsModule {
    private DetailsView mDetailsView;

    public DetailsModule(DetailsView detailsView) {
        this.mDetailsView = detailsView;
    }

    @Provides
    public DetailsView provideMainView() {
        return mDetailsView;
    }

    @Provides
    public DetailsPresenter provideMainPresenter(DetailsView detailsView,
                                                 ProducersRepository producersRepository,
                                                 ProducersManager producersManager) {
        return new DetailsPresenterImpl(detailsView,
                producersRepository,
                producersManager);
    }
}