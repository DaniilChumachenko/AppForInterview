package com.chumachenko.appforinterview.data.db

import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import io.reactivex.Maybe

interface StayPlusDataSource<M, L> : LocalDataSource<M, L> {

    fun findByIdSync(id: String): L?

    fun search(search: String, isFollowing: Boolean): Maybe<List<L>>

    fun deleteSync(id: String)

    fun findByIdRealmObject(itemId: String): M?

    fun addOrReplaceSync(model: StayPlusItemRealm): L
}