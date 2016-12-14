package com.example.danielatienza.farmdropapp.presenter.main;

import android.support.v7.widget.GridLayoutManager;

import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.ui.main.MainView;
import com.example.danielatienza.farmdropapp.utils.listener.OnDataRequestListener;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import java.util.List;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class MainPresenterImpl implements MainPresenter, OnDataRequestListener {

        private MainView mMainView;
        private ProducersRepository mProducersRepository;
        private ProducersManager mProducersManager;

        private OnMainRecyclerViewListener mOnMainRecyclerViewListener;

        public MainPresenterImpl(MainView mainView, ProducersRepository producersRepository,
                                 ProducersManager producersManager) {

            this.mMainView = mainView;
            this.mProducersRepository = producersRepository;
            this.mProducersManager = producersManager;
        }

        @Override
        public void defineViewsBehaviour() {

            /* MAKES THE FIRST CALL */
            loadNextPage();
        }

        @Override
        public void loadNextPage() {
            if((mImageAdapter.getItemCount()) < (mImageRepository.countImageDataByType(mType)) &&
                    (mImageRepository.countImageDataByType(mType) > ImageRepository.SELECT_LIMIT)) {

                loadDataFromDB(mImageRepository.getPagedImageData(mImageAdapter.getItemCount(), mType));

            } else {

                mMainView.showProgress();
                mShutterStockManager.loadImageData(this, mType);

            }
        }

        @Override
        public void onDataError() {
            mMainView.hideProgress();
            mMainView.onError();
        }

        @Override
        public void requestData() {
            loadNextPage();
        }

        @Override
        public void onCompleted() {
            loadDataFromDB(mImageRepository.getPagedImageData(mImageAdapter.getItemCount(), mType));
        }


        /*
         RETRIEVE UPDATED DATA FROM DATABASE
         */
        private void loadDataFromDB(List<ImageData> imageDataList) {
            mImageAdapter.addDataSet(imageDataList);
            mImageAdapter.notifyDataSetChanged();
            mMainView.hideProgress();
        }

        /*
         CLEANS THE ADAPTER TO RELOAD DATA
         */
        private void resetAdapter() {
            mOnMainRecyclerViewListener.reset();
            mImageAdapter.reset();
        }

        public class OnMainRecyclerViewListener extends OnRecyclerViewThresholdListener {

            public OnMainRecyclerViewListener(GridLayoutManager mGridLayoutManager) {
                super(mGridLayoutManager);
            }

            @Override
            public void onVisibleThreshold() {
                if(!isReset()) {
                    loadNextPage();
                }
            }
        }
    }
}
