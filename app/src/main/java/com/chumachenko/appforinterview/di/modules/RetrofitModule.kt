package com.chumachenko.appforinterview.di.modules

import com.chumachenko.appforinterview.data.api.StayPlusApi
import com.chumachenko.appforinterview.data.api.RetrofitClient
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRequestService(): Retrofit = RetrofitClient().getInstance()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): StayPlusApi = retrofit.create(StayPlusApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }

}
