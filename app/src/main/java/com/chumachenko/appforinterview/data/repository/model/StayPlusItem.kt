package com.chumachenko.appforinterview.data.repository.model

import android.os.Parcelable
import com.chumachenko.appforinterview.data.db.realmModel.StayPlusItemRealm
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StayPlusItem(
    var image: String = ""
) : Parcelable