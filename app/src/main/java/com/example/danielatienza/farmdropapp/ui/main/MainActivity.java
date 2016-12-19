package com.example.danielatienza.farmdropapp.ui.main;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.danielatienza.farmdropapp.R;
import com.example.danielatienza.farmdropapp.di.component.AppComponent;
import com.example.danielatienza.farmdropapp.di.component.DaggerMainComponent;
import com.example.danielatienza.farmdropapp.di.module.MainModule;
import com.example.danielatienza.farmdropapp.presenter.main.MainPresenter;
import com.example.danielatienza.farmdropapp.ui.base.BaseActivity;
import com.example.danielatienza.farmdropapp.utils.DialogFactory;
import com.example.danielatienza.farmdropapp.utils.ViewUtil;
import com.example.danielatienza.farmdropapp.utils.listener.OnProducerTouchListener;
import com.example.danielatienza.farmdropapp.utils.listener.OnRecyclerViewThresholdListener;
import com.example.danielatienza.farmdropapp.utils.manager.CallManager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.ProducerData;

public class MainActivity extends BaseActivity implements MainView, OnProducerTouchListener {

    @Inject CallManager mCallManager;
    @Inject MainPresenter mMainPresenter;
    @Inject ProducersAdapter mProducersAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolBar;
    @BindView(R.id.loading_layout) RelativeLayout mLoadingLayout;
    @BindView(R.id.searchView) MaterialSearchView mSearchView;

    private OnMainRecyclerViewListener mOnMainRecyclerViewListener;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnCreated(AppComponent appComponent, Bundle saveInstanceState) {

        ButterKnife.bind(this);

        /* Defines toolbar home button and title */
        setSupportActionBar(mToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

         /* Inject Dependency (Component) */

        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);


        mProducersAdapter.setOnProducerTouchListener(this);
        mRecyclerView.setAdapter(mProducersAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mOnMainRecyclerViewListener =
                new OnMainRecyclerViewListener(mLinearLayoutManager);

        mRecyclerView.addOnScrollListener(mOnMainRecyclerViewListener);

        mMainPresenter.attachView(this);
        mMainPresenter.loadProducers();

        setSearchView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }


    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Sets up the SearchView at the toolbar
     */
    private void setSearchView() {
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setVoiceSearch(false);
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                ViewUtil.changeStatusBarColor(R.color.default_grey, getWindow(), MainActivity.this);
                mMainPresenter.onSearchShown();
            }

            @Override
            public void onSearchViewClosed() {
                mMainPresenter.onSearchClosed();
            }
        });

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ViewUtil.hideKeyboard(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return mMainPresenter.onSearch(newText);
            }
        });
    }

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_producers)).show();
    }

    @Override
    public void showProducers(List<ProducerData> producers) {
        mProducersAdapter.addProducers(producers);
        mProducersAdapter.notifyDataSetChanged();
    }

    @Override
    public void resetAdapter() {
        mOnMainRecyclerViewListener.reset();
        mProducersAdapter.reset();
    }

    @Override
    public int getItemsCount() {
        return mProducersAdapter.getItemCount();
    }

    @Override
    public void onProducerTouched(long producerId, Pair<View, String>... params) {
        mCallManager.startDetailsActivity(this, producerId, params);
    }

    public class OnMainRecyclerViewListener extends OnRecyclerViewThresholdListener {

        public OnMainRecyclerViewListener(LinearLayoutManager mLayoutManager) {
            super(mLayoutManager);
        }

        @Override
        public void onVisibleThreshold() {
            if(!isReset()) {
                mMainPresenter.loadNextPage();
            }
        }
    }
}
