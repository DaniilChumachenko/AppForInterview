package com.chumachenko.appforinterview.data.repository.repositoryImpl

import com.chumachenko.appforinterview.data.api.StayPlusApi
import com.chumachenko.appforinterview.data.db.StayPlusLocalSource
import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import com.chumachenko.appforinterview.data.repository.StayPlusRepository
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class StayPlusRepositoryImpl(
    private val api: StayPlusApi,
    private val stayPlusDataSource: StayPlusLocalSource
) : StayPlusRepository {

    override fun getStayPlusByLocal(
    ): Single<List<StayPlusItem>> =
        api.getStayPlusData()
            .subscribeOn(Schedulers.io())
            .map {
                stayPlusDataSource.addOrReplace(it.toRealm())
                it.toItem()
            }

    override fun getFromLocal(): Observable<ArrayList<StayPlusItem>> =
        stayPlusDataSource.findAll().toObservable()

    override fun clearStorage(listItem: ArrayList<StayPlusItemRealm>): Observable<ArrayList<StayPlusItem>> =
        stayPlusDataSource.delete(listItem).toObservable()
}