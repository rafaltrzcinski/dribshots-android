package com.rafaltrzcinski.dribshots.shots.details

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.rafaltrzcinski.dribshots.rest.model.Shot
import com.rafaltrzcinski.dribshots.shots.list.ShotsActivityContract

class ShotDetailsViewModel(private val shot: Shot, private val presenter: ShotsActivityContract.Presenter) : BaseObservable() {

    @Bindable
    fun getImage() = shot.images.normal

    @Bindable
    fun getTitle() = shot.title

    @Bindable
    fun getDescription() = shot.description ?: ""

    @Bindable
    fun getUserAvatarUrl() = shot.user.avatarUrl

    @Bindable
    fun getUserName() = shot.user.name

    @Bindable
    fun getTeamName() = shot.team?.name ?: "No team"

    @Bindable
    fun getTeamAvatarUrl() = shot.team?.avatarUrl ?: ""

    @Bindable
    fun getViews() = "${shot.viewsCount}"

    @Bindable
    fun getLikes() = "${shot.likesCount}"

    @Bindable
    fun getComments() = "${shot.commentsCount}"

    fun hideDetails() = presenter.hideShotDetails()
}