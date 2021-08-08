package com.chumachenko.appforinterview.di

import android.app.Application
import com.chumachenko.appforinterview.App
import com.chumachenko.appforinterview.di.modules.AppModule
import com.chumachenko.appforinterview.di.modules.PresentationModule
import com.chumachenko.appforinterview.di.modules.RetrofitModule
import com.chumachenko.appforinterview.di.repository.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            PresentationModule::class,
            RepositoryModule::class,
            RetrofitModule::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}