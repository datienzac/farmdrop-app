package com.example.danielatienza.farmdropapp.di.component;

import com.example.danielatienza.farmdropapp.di.module.MainModule;
import com.example.danielatienza.farmdropapp.di.scope.Activity;
import com.example.danielatienza.farmdropapp.presenter.main.MainPresenter;
import com.example.danielatienza.farmdropapp.ui.main.MainActivity;
import com.example.danielatienza.farmdropapp.ui.main.ProducersAdapter;

import dagger.Component;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    MainPresenter provideMainPresenter();

}