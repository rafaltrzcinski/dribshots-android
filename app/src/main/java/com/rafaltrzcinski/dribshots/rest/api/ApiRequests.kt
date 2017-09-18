package com.rafaltrzcinski.dribshots.rest.api

import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.Flowable
import retrofit2.http.GET

interface ApiRequests {

    @GET("shots")
    fun getShots(): Flowable<List<Shot>>

}