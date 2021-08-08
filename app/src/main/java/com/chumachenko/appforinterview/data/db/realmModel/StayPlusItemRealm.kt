package com.chumachenko.appforinterview.data.db.realmModel

import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StayPlusItemRealm(
    @PrimaryKey
    var image: String = "",
) : RealmObject() {
    fun toStayPlusItem() =
        StayPlusItem(image)
}
