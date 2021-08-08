package com.chumachenko.appforinterview.data.api

import com.chumachenko.appforinterview.data.api.response.StayPlusResponse
import io.reactivex.Single
import retrofit2.http.*

interface StayPlusApi {
    @GET("api/v17/app_start")
    fun getStayPlusData(): Single<StayPlusResponse>
}