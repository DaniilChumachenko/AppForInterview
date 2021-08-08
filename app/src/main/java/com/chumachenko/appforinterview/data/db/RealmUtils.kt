package com.chumachenko.appforinterview.data.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmUtils {
    fun initRealm(context: Context?) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .compactOnLaunch()
            .name("appforinterview.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}