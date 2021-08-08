package com.chumachenko.appforinterview.di

import com.chumachenko.appforinterview.presentation.fragment.SearchFragment
import com.chumachenko.appforinterview.presentation.fragment.StayPlusFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector()
    abstract fun provideStayPlusFragment (): StayPlusFragment

    @ContributesAndroidInjector()
    abstract fun provideSearchFragment (): SearchFragment

}