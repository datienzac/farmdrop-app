package com.example.danielatienza.farmdropapp.ui.details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.danielatienza.farmdropapp.R;
import com.example.danielatienza.farmdropapp.di.component.AppComponent;
import com.example.danielatienza.farmdropapp.di.component.DaggerDetailsComponent;
import com.example.danielatienza.farmdropapp.di.module.DetailsModule;
import com.example.danielatienza.farmdropapp.presenter.details.DetailsPresenter;
import com.example.danielatienza.farmdropapp.ui.base.BaseActivity;
import com.example.danielatienza.farmdropapp.utils.DialogFactory;
import com.example.danielatienza.farmdropapp.utils.manager.CallManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class DetailsActivity extends BaseActivity implements DetailsView {

    private ProducerData mProducerData;

    @Inject DetailsPresenter mDetailsPresenter;

    @BindView(R.id.toolbar) Toolbar mToolBar;
    @BindView(R.id.image_view) ImageView mImage;
    @BindView(R.id.text_name) TextView mName;
    @BindView(R.id.text_location) TextView mLocation;
    @BindView(R.id.text_description) TextView mDescription;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_details;
    }

    @Override
    protected void doOnCreated(AppComponent appComponent, Bundle saveInstanceState) {

        ButterKnife.bind(this);

        long producerId = getIntent().getLongExtra(CallManager.PRODUCER_ID, -1);


        /* Draw Over Status Bar*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        /* Defines Toolbar and backbutton */

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /* Inject Dependency (Component) */

        DaggerDetailsComponent
                .builder()
                .appComponent(appComponent)
                .detailsModule(new DetailsModule(this))
                .build()
                .inject(this);

        /* Creates the link to the transition animation */

        ViewCompat.setTransitionName(mImage, CallManager.IMAGE);

        mDetailsPresenter.attachView(this);
        mDetailsPresenter.loadProducer(producerId);
    }

    @Override
    public void showProgress() {
        //mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //mLoadingLayout.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_producer_details)).show();
        //finish();
    }

    @Override
    public void showProducer(ProducerData producerData) {
        this.mProducerData = producerData;
       /* IMAGE */
        Glide.with(this)
                .load(producerData.getProducerImageURL())
                .thumbnail(Glide.with(this).load(producerData.getProducerImageURL()).centerCrop().dontAnimate())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .centerCrop()
                .into(mImage);

        mName.setText(producerData.getProducerName());
        mLocation.setText(producerData.getProducerLocation());
        mDescription.setText(producerData.getProducerDescription());
    }

}