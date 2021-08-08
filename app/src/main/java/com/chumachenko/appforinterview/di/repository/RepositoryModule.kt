package com.chumachenko.appforinterview.di.repository

import com.chumachenko.appforinterview.data.api.StayPlusApi
import com.chumachenko.appforinterview.data.db.StayPlusLocalSource
import com.chumachenko.appforinterview.data.repository.StayPlusRepository
import com.chumachenko.appforinterview.data.repository.repositoryImpl.StayPlusRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStayPlusRepository(
        api: StayPlusApi,
        stayPlusSource: StayPlusLocalSource
    ): StayPlusRepository = StayPlusRepositoryImpl(
        api,
        stayPlusSource
    )
}