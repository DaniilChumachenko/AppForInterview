package com.chumachenko.appforinterview.data.api.response

import android.os.Parcelable
import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import com.chumachenko.appforinterview.data.repository.model.StayPlusItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StayPlusResponse(
    val data: Data,
    val error: Int
): Parcelable {
    fun toRealm(): List<StayPlusItemRealm> = data.toRealm()

    fun toItem(): List<StayPlusItem> = data.toItem()
}