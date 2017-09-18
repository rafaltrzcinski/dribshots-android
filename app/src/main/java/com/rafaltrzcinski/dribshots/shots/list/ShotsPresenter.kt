package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class ShotsPresenter(
        private val apiRequests: ApiRequests,
        private val subscribeOn: Scheduler,
        private val observeOn: Scheduler
) : ShotsActivityContract.Presenter {

    private var view: ShotsActivityContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun bind(view: ShotsActivityContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
        compositeDisposable.clear()
    }

    override fun getShots() {
        val disposable = apiRequests.getShots()
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribe(
                        { shots -> view?.loadShots(shots) },
                        {
                            view?.showLoadingError()
                            view?.finishLoading()
                        },
                        { view?.finishLoading() }
                )
        compositeDisposable.addAll(disposable)
    }

    override fun openShotDetails(shot: Shot) {
        view?.attachShotDetails(shot)
    }
}