package com.example.danielatienza.farmdropapp.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.example.danielatienza.farmdropapp.App;
import com.example.danielatienza.farmdropapp.di.component.AppComponent;

/**
 * Created by danielatienza on 14/12/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutToInflate());
        doOnCreated(((App) getApplication()).getAppComponent(), savedInstanceState);
    }

    protected abstract @LayoutRes
    int layoutToInflate();

    protected abstract void doOnCreated(AppComponent appComponent, Bundle saveInstanceState);

}