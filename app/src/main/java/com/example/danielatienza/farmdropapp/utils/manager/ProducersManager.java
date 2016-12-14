package com.example.danielatienza.farmdropapp.utils.manager;

import com.crashlytics.android.Crashlytics;
import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.model.Producer;
import com.example.danielatienza.farmdropapp.model.Response;
import com.example.danielatienza.farmdropapp.network.ProducersInterface;
import com.example.danielatienza.farmdropapp.utils.listener.OnDataRequestListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import greendao.ProducerData;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class ProducersManager {

    public static final int CONNECTION_TIMEOUT = 60;

    private static final long INITIAL_PAGE = 1;
    private static final int TOTAL_RETRIES = 3;

    /*
     CREATED A DIFFERENT INDEX PAGE FOR ALL OBJECTS BECAUSE IT COULD NOT BE RELATED TO THE SIZE OF
     THE TABLE, BECAUSE OTHER TYPES COULD LOAD OBJECTS
     */
    private long mCurrentPage = INITIAL_PAGE;

    private ProducersInterface mProduersInterface;
    private ParserManager mParserManager;
    private ProducersRepository mProducersRepository;

    public ProducersManager(ProducersInterface ProducersInterface,
                            ParserManager parserManager,
                            ProducersRepository producersRepository) {
        this.mProduersInterface = ProducersInterface;
        this.mParserManager = parserManager;
        this.mProducersRepository = producersRepository;
    }

    /*
     IMAGE SEARCH SECTION
     */
    public void loadProducersData(final OnDataRequestListener onDataRequestListener, int type) {

        /*
         PARAMETERS USED TO LOAD THE PRODUCERS FROM FARMDROP API
         PAGE: CURRENT PAGE VARIABLE
         PARAMETER_PER_PAGE_LIMIT: NUMBER OF ITEMS PER PAGE LIMIT
         */
        Map<String, String> parameters = new HashMap<>();

        parameters.put(ProducersInterface.PARAMETER_PAGE, String.valueOf(mCurrentPage++));
        parameters.put(ProducersInterface.PARAMETER_PER_PAGE_LIMIT, "10");

        /* REQUESTS THE IMAGE'S DATA */
        mProduersInterface
                .producersList(parameters)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                /* SEPARATES THE IMAGES LIST FROM THE RESPONSE */
                .flatMap(new Func1<Response, Observable<Producer>>() {
                    @Override
                    public Observable<Producer> call(Response responseParser) {
                        return Observable.from(responseParser.getProducers());
                    }
                })
                /* TRANSFORMS THE DATA INTO AN PRODUCER DATA OBSERVABLE */
                .flatMap(new Func1<Producer, Observable<ProducerData>>() {
                    @Override
                    public Observable<ProducerData> call(Producer data) {
                        return Observable.just(mParserManager.parseProducers(data));
                    }
                })
                /* DEFINE RETRY (3 TIMES) */
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer attempts, Throwable throwable) {
                        return attempts <= TOTAL_RETRIES;
                    }
                })
                /*
                 TRANSFORMS THE DATA INTO A LIST TO RECEIVE THE ANSWER JUST WHEN THE ENTIRE LIST
                 IS LOADED
                 */
                .toList()
                /* DEFINES A REQUEST TIMEOUT */
                .timeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                /* SENDS THE DATA TO THE MAIN PRESENTER */
                .subscribe(new Observer<List<ProducerData>>() {

                    private boolean recall = false;

                    @Override
                    public void onCompleted() {
                        if (recall) {
                            /* RECALLS THE SERVICE INSIDE THE SAME THREAD */
                            onDataRequestListener.requestData();
                        } else {

                            onDataRequestListener.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.log(e.getMessage());
                        onDataRequestListener.onDataError();
                    }

                    @Override
                    public void onNext(List<ProducerData> producerDataSet) {
                        long previousTotal = mProducersRepository.countProducerData();
                        /* PERSIST DATA ON DATABASE TO REQUEST AFTER COMPLETED */
                        mProducersRepository.insertOrReplaceInTx(producerDataSet);
                        long currentTotal = mProducersRepository.countProducerData();

                        /*
                         CHECK IF WAS ADDED NEW RECORDS
                         BECAUSE IT'S POSSIBLE LOAD AN ALREADY RECORDED PAGE CONSIDERING THE FILTERS
                         IN THAT CASE MAKES ANOTHER SEARCH
                        */
                        if (previousTotal == currentTotal) {
                            recall = true;
                        }
                    }
                });

    }


    public void resetPageSearch() {
        mCurrentPage = INITIAL_PAGE;
    }

//    /*
//     THREAD CREATED TO SAVE THE IMAGE'S CATEGORIES INTO THE DATABASE
//     */
//    public class CategoryPersistence implements Runnable {
//
//        private Data mData;
//
//        public CategoryPersistence(Data data) {
//            this.mData = data;
//        }
//
//        @Override
//        public void run() {
//            mCategoryRepository.deleteCategoryDataForImageId(mData.getId());
//            mCategoryRepository.insertOrReplaceInTx(mParserManager.parseCategories(mData));
//        }
//    }

}