package com.example.danielatienza.farmdropapp.presenter.main;

import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.ui.base.BasePresenter;
import com.example.danielatienza.farmdropapp.ui.base.MvpView;
import com.example.danielatienza.farmdropapp.ui.main.MainView;
import com.example.danielatienza.farmdropapp.utils.listener.OnDataRequestListener;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import java.util.List;

import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class MainPresenterImpl extends BasePresenter implements MainPresenter, OnDataRequestListener {

    private MainView mMainView;
    private ProducersRepository mProducersRepository;
    private ProducersManager mProducersManager;
    private boolean searching;

    public MainPresenterImpl(MainView mainView, ProducersRepository producersRepository,
                             ProducersManager producersManager) {

        this.mMainView = mainView;
        this.mProducersRepository = producersRepository;
        this.mProducersManager = producersManager;
    }

    @Override
    public void attachView(MvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    @Override
    public void loadProducers() {
        /* Makes the first call */
        loadNextPage();
    }

    @Override
    public void loadNextPage() {
        int itemsCount = mMainView.getItemsCount();
        if (!searching) {
            if((itemsCount < mProducersRepository.countProducerData()) &&
                    (mProducersRepository.countProducerData() > ProducersRepository.SELECT_LIMIT)) {

                loadDataFromDB(mProducersRepository.getPagedProducerData(itemsCount));
            } else {

                mMainView.showProgress();
                mProducersManager.loadProducersData(this);

            }

        }
    }

    /*
     * Retrieve updated data from db
     */
    private void loadDataFromDB(List<ProducerData> producerDataList) {
        mMainView.showProducers(producerDataList);
        mMainView.hideProgress();
    }

    @Override
    public void onRefresh() {
        /* TODO: Swipe to Refresh Behaviour*/

//                .setOnRefreshListener(
//                        new SwipeRefreshLayout
//                                .OnRefreshListener() {
//                            @Override
//                            public void onRefresh() {
//                                mProducersRepository.clearProducerData();
//                                mProducersManager.resetPageSearch();
//                                loadNextPage();
//                                resetAdapter();
//                            }
//                        });
    }

    @Override
    public boolean onSearch(String newText) {
        if (newText.length() > 0) {
            mMainView.resetAdapter();
            mMainView.showProducers(mProducersRepository.getProducerDataByName(newText));
            return false;
        }
        return true;
    }

    @Override
    public void onSearchClosed() {
        searching = false;
        mMainView.resetAdapter();
        mProducersManager.resetPageSearch();
        loadNextPage();
    }

    @Override
    public void onSearchShown() {
        searching = true;
    }

    @Override
    public void onDataError() {
        mMainView.hideProgress();
        mMainView.onError();
    }

    @Override
    public void onCompleted() {
        loadDataFromDB(mProducersRepository.getPagedProducerData(mMainView.getItemsCount()));
    }

    @Override
    public void requestData() {
        loadNextPage();
    }

}

