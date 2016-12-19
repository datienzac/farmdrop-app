package com.example.danielatienza.farmdropapp.utils.manager;

import com.example.danielatienza.farmdropapp.model.Producer;

import greendao.ProducerData;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class ParserManager {

    /* PARSE DATA AND PERSIST THE CATEGORY ON DATABASE */
    public ProducerData parseProducers(Producer producer) {
        /* PARSE DATA RETRIEVED TO IMAGE DATA */
        ProducerData producerData = new ProducerData();

        producerData.setProducerId(producer.getId());
        producerData.setProducerName(producer.getName());
        producerData.setProducerLocation(producer.getLocation());
        producerData.setProducerDescription(producer.getDescription());
        producerData.setProducerImageURL(producer.getImages().get(0).getPath());

        return producerData;
    }

}