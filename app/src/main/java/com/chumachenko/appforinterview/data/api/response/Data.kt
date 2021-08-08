package com.chumachenko.appforinterview.data.api.response

import android.os.Parcelable
import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Data(
    val news_groups: List<NewsGroup>
): Parcelable {
    fun toRealm(): List<StayPlusItemRealm>
        {
            val list = arrayListOf<StayPlusItemRealm>()
            news_groups.forEach {
                list.add(it.toRealm())
            }
            return list
        }

    fun toItem(): List<StayPlusItem>
        {
            val list = arrayListOf<StayPlusItem>()
            news_groups.forEach {
                list.add(it.toRealm().toStayPlusItem())
            }
            return list
        }

}