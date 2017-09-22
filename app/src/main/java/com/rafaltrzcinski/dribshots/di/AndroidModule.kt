package com.rafaltrzcinski.dribshots.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides

@Module
class AndroidModule(private val app: Application) {

    @Provides
    @AppScope
    fun provideSharedPreferences(): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(app)
}