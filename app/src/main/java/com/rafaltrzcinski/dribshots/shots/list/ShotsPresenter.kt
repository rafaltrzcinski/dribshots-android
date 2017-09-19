package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import com.rafaltrzcinski.dribshots.rest.model.Shot
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Headers

class ShotsPresenter(
        private val apiRequests: ApiRequests,
        private val subscribeOn: Scheduler,
        private val observeOn: Scheduler
) : ShotsActivityContract.Presenter {

    private var view: ShotsActivityContract.View? = null
    private val compositeDisposable = CompositeDisposable()
    private var currentNext: String = ""

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
                        { result ->
                            view?.loadShots(result.response()?.body()!!)
                            setCurrentNextLink(result.response()?.headers())
                        },
                        {
                            view?.showLoadingError()
                            view?.finishLoading()
                        },
                        { view?.finishLoading() }
                )
        compositeDisposable.add(disposable)
    }


    override fun getNextShots() {
        if (currentNext.isNotEmpty()) {
            view?.startLoading()
            val disposable = apiRequests.getShots(currentNext)
                    .subscribeOn(subscribeOn)
                    .observeOn(observeOn)
                    .subscribe(
                            { result ->
                                view?.loadNextShots(result.response()?.body()!!)
                                setCurrentNextLink(result.response()?.headers())
                            },
                            {
                                view?.showLoadingError()
                                view?.finishLoading()
                            },
                            { view?.finishLoading() }
                    )
            compositeDisposable.add(disposable)
        }
    }

    override fun setCurrentNextLink(headers: Headers?) {
        val linksHeader =
                if (headers != null) {
                    headers["link"] ?: ""
                } else {
                    ""
                }
        currentNext = getNextUrlFromLinkHeader(linksHeader)
    }

    override fun openShotDetails(shot: Shot) {
        view?.attachShotDetails(shot)
    }

    override fun getNextUrlFromLinkHeader(linkHeader: String?): String {
        if (linkHeader != null && linkHeader.isNotEmpty()) {
            val resultMap = mutableMapOf<String, String>()

            linkHeader
                    .split(delimiters = ",", ignoreCase = true)
                    .map {
                        it.replace("<", "").replace(">", "").replace(" ", "").replace("\"", "")
                    }
                    .map { it.split(delimiters = ";", ignoreCase = true) }
                    .map {
                        val relString = it[1].replace("rel=", "")
                        resultMap.put(relString, it[0])
                    }
            return resultMap["next"] ?: ""
        } else {
            return ""
        }
    }
}