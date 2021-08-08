package com.chumachenko.appforinterview.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chumachenko.appforinterview.presentation.viewmodel.StayPlusViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StayPlusViewModel::class)
    internal abstract fun bindFirstViewModel(viewModel: StayPlusViewModel): ViewModel


}