package com.example.danielatienza.farmdropapp.database;

import java.util.List;
import greendao.DaoSession;
import greendao.ProducerData;
import greendao.ProducerDataDao;

/**
 * Created by danielatienza on 14/12/2016.
 */

public class ProducersRepository {
    public static final int SELECT_LIMIT = 10;

    private DaoSession mDaoSession;

    public ProducersRepository(DaoSession daoSession) {
        this.mDaoSession = daoSession;
    }

    public List<ProducerData> getAllProducers() {
        return mDaoSession.getProducerDataDao().loadAll();
    }

    public void insertOrReplace(ProducerData ProducerData) {
        mDaoSession.getProducerDataDao().insertOrReplace(ProducerData);
    }

    public void insertOrReplaceInTx(List<ProducerData> ProducerData) {
        mDaoSession.getProducerDataDao().insertOrReplaceInTx(ProducerData);
    }

    public List<ProducerData> getProducerDataByName(String producerName) {
        return mDaoSession.getProducerDataDao()
                .queryBuilder().where(ProducerDataDao.Properties.ProducerName.like("%" + producerName + "%")).list();
    }

    public ProducerData getProducerDataById(long producerId) {
        return mDaoSession.getProducerDataDao()
                .queryBuilder().where(ProducerDataDao.Properties.ProducerId.eq(producerId)).unique();
    }

    public List<ProducerData> getPagedProducerData(int offset) {
        return mDaoSession.getProducerDataDao()
                .queryBuilder()
                .limit(SELECT_LIMIT).offset(offset).list();
    }

    public long countProducerData() {
        return mDaoSession.getProducerDataDao().count();
    }

    public void clearProducerData() {
        mDaoSession.getProducerDataDao().deleteAll();
    }

}