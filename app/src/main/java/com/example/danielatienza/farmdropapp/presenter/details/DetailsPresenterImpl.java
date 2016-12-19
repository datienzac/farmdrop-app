package com.example.danielatienza.farmdropapp.presenter.details;

import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.ui.base.BasePresenter;
import com.example.danielatienza.farmdropapp.ui.base.MvpView;
import com.example.danielatienza.farmdropapp.ui.details.DetailsView;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class DetailsPresenterImpl extends BasePresenter implements DetailsPresenter {

    private ProducerData mProducerData;

    private DetailsView mDetailsView;
    private ProducersRepository mProducersRepository;
    private ProducersManager mProducersManager;

    public DetailsPresenterImpl(DetailsView detailsView, ProducersRepository imageRepository,
                                ProducersManager producersManager) {

        this.mDetailsView = detailsView;
        this.mProducersRepository = imageRepository;
        this.mProducersManager = producersManager;
        }

    @Override
    public void loadProducer(long producerId) {
        mDetailsView.showProgress();

        /* RETRIEVES AND LOAD INTO THE LAYOUT THE IMAGE DATA */

        mProducerData = mProducersRepository.getProducerDataById(producerId);
        mDetailsView.showProducer(mProducerData);
        mDetailsView.hideProgress();
    }

    @Override
    public void attachView(MvpView view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

}

