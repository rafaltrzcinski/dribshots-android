package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.Scheduler

class ShotsPresenter(
        private val apiRequests: ApiRequests,
        private val subscribeOn: Scheduler,
        private val observeOn: Scheduler
) : ShotsActivityContract.Presenter {

    private var view: ShotsActivityContract.View? = null

    override fun bind(view: ShotsActivityContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getShots() {
        apiRequests.getShots()
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(
                        { shots -> view?.loadShots(shots) },
                        { view?.showLoadingError() }
                )
    }

    override fun openShotDetails(shot: Shot) {
        view?.attachShotDetails(shot)
    }
}