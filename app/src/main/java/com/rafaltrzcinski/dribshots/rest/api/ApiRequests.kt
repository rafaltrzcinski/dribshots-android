package com.rafaltrzcinski.dribshots.rest.api

import io.reactivex.Flowable
import retrofit2.http.GET

interface ApiRequests {

    @GET("shots")
    fun getShots(): Flowable<String>

}