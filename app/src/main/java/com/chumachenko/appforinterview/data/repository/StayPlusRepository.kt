package com.chumachenko.appforinterview.data.repository

import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import io.reactivex.Observable
import io.reactivex.Single


interface StayPlusRepository {

    fun getStayPlusByLocal(): Single<List<StayPlusItem>>

    fun getFromLocal(): Observable<ArrayList<StayPlusItem>>

    fun clearStorage(listItem: ArrayList<StayPlusItemRealm>): Observable<ArrayList<StayPlusItem>>
}