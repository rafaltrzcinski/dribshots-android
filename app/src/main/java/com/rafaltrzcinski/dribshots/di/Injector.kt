package com.rafaltrzcinski.dribshots.di

import android.app.Application

class Injector {

    companion object {
        lateinit var component: AppComponent
            private set
    }

    fun start(application: Application) {
        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(application))
                .androidModule(AndroidModule(application))
                .build()
    }
}