package com.rafaltrzcinski.dribshots.rest.api

import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.Flowable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiRequests {

    @GET("shots")
    fun getShots(): Flowable<Result<List<Shot>>>

    @GET
    fun getShots(@Url link: String): Flowable<Result<List<Shot>>>

}