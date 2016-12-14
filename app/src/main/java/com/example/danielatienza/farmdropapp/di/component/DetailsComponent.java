package com.example.danielatienza.farmdropapp.di.component;

import com.example.danielatienza.farmdropapp.di.module.DetailsModule;
import com.example.danielatienza.farmdropapp.di.scope.Activity;
import com.example.danielatienza.farmdropapp.presenter.details.DetailsPresenter;
import com.example.danielatienza.farmdropapp.ui.details.DetailsActivity;

import dagger.Component;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = DetailsModule.class)
public interface DetailsComponent {

    void inject(DetailsActivity detailsActivity);

    DetailsPresenter provideDetailsPresenter();

}