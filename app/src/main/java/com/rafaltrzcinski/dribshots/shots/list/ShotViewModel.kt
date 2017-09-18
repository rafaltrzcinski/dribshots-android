package com.rafaltrzcinski.dribshots.shots.list

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.rafaltrzcinski.dribshots.rest.model.Shot

class ShotViewModel(private val shot: Shot, private val presenter: ShotsActivityContract.Presenter) : BaseObservable() {

    @Bindable
    fun getImage() = shot.images.normal

    @Bindable
    fun getTitle() = shot.title

    fun onShotClick() = presenter.openShotDetails(shot)
}