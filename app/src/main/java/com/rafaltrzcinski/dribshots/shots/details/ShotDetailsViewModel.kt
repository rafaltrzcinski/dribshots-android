package com.rafaltrzcinski.dribshots.shots.details

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.rafaltrzcinski.dribshots.rest.model.Shot

class ShotDetailsViewModel(private val shot: Shot) : BaseObservable() {

    @Bindable
    fun getImage() = shot.images.normal

    @Bindable
    fun getTitle() = shot.title

    @Bindable
    fun getDescription() = shot.description ?: ""
}