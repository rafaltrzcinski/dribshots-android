package com.rafaltrzcinski.dribshots.shots

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.rafaltrzcinski.dribshots.rest.model.Shot

class ShotViewModel(private val shot: Shot) : BaseObservable() {

    @Bindable
    fun getImage() = shot.images.normal

    @Bindable
    fun getTitle() = shot.title
}