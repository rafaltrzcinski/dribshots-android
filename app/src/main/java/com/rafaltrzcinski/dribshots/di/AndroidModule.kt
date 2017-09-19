package com.rafaltrzcinski.dribshots.di

import android.app.Application
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides

@Module
class AndroidModule(private val app: Application) {

    @Provides
    @AppScope
    fun provideSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(app)
}