package com.rafaltrzcinski.dribshots.di

import android.app.Application
import android.content.SharedPreferences
import com.rafaltrzcinski.dribshots.controllers.UserController
import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.shots.list.ShotsActivityContract
import dagger.Component

@AppScope
@Component(modules = arrayOf(AppModule::class, AndroidModule::class))
interface AppComponent {

    fun inject(application: Application)

    fun getApiRequests(): ApiRequests

    fun getSharedPrefs(): SharedPreferences

    fun getUserController(): UserController

    fun getShotsListPresenter(): ShotsActivityContract.Presenter
}