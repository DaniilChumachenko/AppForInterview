package com.chumachenko.appforinterview

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.chumachenko.appforinterview.data.db.RealmUtils
import com.chumachenko.appforinterview.di.AppComponent
import com.chumachenko.appforinterview.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    var appComponent: AppComponent? = null

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        initRealm()
        super.onCreate()
        initDagger()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun initDagger() {
        this.appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        this.appComponent!!
            .inject(this)
    }

    private fun initRealm() {
        RealmUtils().initRealm(this)
    }

}