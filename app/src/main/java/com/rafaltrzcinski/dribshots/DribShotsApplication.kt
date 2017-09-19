package com.rafaltrzcinski.dribshots

import android.support.multidex.MultiDexApplication
import com.rafaltrzcinski.dribshots.di.Injector

class DribShotsApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Injector().start(this)
    }
}