package com.chumachenko.appforinterview.di.modules

import com.chumachenko.appforinterview.di.ActivityBuilder
import com.chumachenko.appforinterview.di.viewModel.ViewModelModule
import dagger.Module

@Module(
    includes = [
        ActivityBuilder::class,
        ViewModelModule::class
    ]
)
abstract class PresentationModule