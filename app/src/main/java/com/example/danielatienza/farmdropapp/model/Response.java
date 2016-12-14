package com.example.danielatienza.farmdropapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class Response{

    @SerializedName("response")
    private List<Producer> producers;

    private String count;

    private Pagination pagination;

    public List<Producer> getProducers() {
        return producers;
    }

    public Pagination getPagination() {
        return pagination;
    }
}