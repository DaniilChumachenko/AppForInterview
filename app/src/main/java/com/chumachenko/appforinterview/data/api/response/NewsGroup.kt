package com.chumachenko.appforinterview.data.api.response

import android.os.Parcelable
import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NewsGroup(
    val image: String
): Parcelable {
    fun toRealm(): StayPlusItemRealm = StayPlusItemRealm(image)
}