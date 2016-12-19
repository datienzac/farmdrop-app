package com.example.danielatienza.farmdropapp.network;

import com.example.danielatienza.farmdropapp.model.Response;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by danielatienza on 14/12/2016.
 */

public interface ProducersInterface {

    String PRODUCERS_LIST = "producers";
    String PRODUCER = "PRODUCERS_LIST" + "/{id}";
    String PARAMETER_PAGE = "page";
    String PARAMETER_PER_PAGE_LIMIT = "per_page_limit";

    @GET(PRODUCERS_LIST)
    Observable<Response> producersList(@QueryMap Map<String, String> parameters);


    @GET(PRODUCER)
    Observable<Response> getProducer(@Path("id") int producerId);
}