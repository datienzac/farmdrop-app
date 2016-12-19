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
     * Producers Search Section
     */
    public void loadProducersData(final OnDataRequestListener onDataRequestListener) {

        /*
         Parameters used to load the producers from Farmdrop Api
         PAGE: current page available
         PARAMETER_PER_PAGE_LIMIT: Number of items per page limit
         */
        Map<String, String> parameters = new HashMap<>();

        parameters.put(ProducersInterface.PARAMETER_PAGE, String.valueOf(mCurrentPage++));
        parameters.put(ProducersInterface.PARAMETER_PER_PAGE_LIMIT, "10");

        /* REQUESTS THE PRODUCER'S DATA */
        mProduersInterface
                .producersList(parameters)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                /* Separates the Producers list from the response */
                .flatMap(new Func1<Response, Observable<Producer>>() {
                    @Override
                    public Observable<Producer> call(Response responseParser) {
                        return Observable.from(responseParser.getProducers());
                    }
                })
                /* Transforms the data into a producer data oversbable */
                .flatMap(new Func1<Producer, Observable<ProducerData>>() {
                    @Override
                    public Observable<ProducerData> call(Producer data) {
                        return Observable.just(mParserManager.parseProducers(data));
                    }
                })
                /* Define retry (3 Times) */
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer attempts, Throwable throwable) {
                        return attempts <= TOTAL_RETRIES;
                    }
                })
                /*
                 Trans forms the data into a list to receive the answer just when the entire list is loaded
                 */
                .toList()
                /* Defines a request timeout */
                .timeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                /* Sends the data to the main presenter */
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
                    public void onNext(List<ProducerData> producerDataList) {
                        /* Persist data on database to request after completed */
                        mProducersRepository.insertOrReplaceInTx(producerDataList);


                        long previousTotal = mProducersRepository.countProducerData();
                        /* Persist data on database to request after completed */
                        mProducersRepository.insertOrReplaceInTx(producerDataList);
                        long currentTotal = mProducersRepository.countProducerData();

                        /*
                         Check if was added new records
                         Because it's possible load an already recorded page considering the filters
                         in that case makes another search
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


}