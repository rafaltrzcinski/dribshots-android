package com.rafaltrzcinski.dribshots.di

import android.app.Application
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.controllers.UserController
import com.rafaltrzcinski.dribshots.controllers.UserControllerImpl
import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.api.RequestInterceptor
import com.rafaltrzcinski.dribshots.shots.list.ShotsActivityContract
import com.rafaltrzcinski.dribshots.shots.list.ShotsPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication() = app

    @Provides
    @AppScope
    fun provideApiRequests(): ApiRequests {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val requestInterceptor = RequestInterceptor()
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(requestInterceptor)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            baseUrl(app.getString(R.string.base_url))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        return retrofit.create(ApiRequests::class.java)
    }

    @Provides
    @AppScope
    fun provideUserController(): UserController = UserControllerImpl()

    @Provides
    @AppScope
    @Named("subscribeOn")
    fun provideSubscribeOnScheduler(): Scheduler = Schedulers.io()

    @Provides
    @AppScope
    @Named("observeOnMainThread")
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @AppScope
    fun provideShotsListPresenter(
            apiRequests: ApiRequests, @Named("subscribeOn") subscribeOn: Scheduler,
            @Named("observeOnMainThread") observeOn: Scheduler
    ): ShotsActivityContract.Presenter =
            ShotsPresenter(apiRequests, subscribeOn, observeOn)
}