package com.rafaltrzcinski.dribshots.shots

import com.rafaltrzcinski.dribshots.rest.api.ApiRequests
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShotsPresenter(private val apiRequests: ApiRequests): ShotsActivityContract.Presenter {

    override fun getShots() {
        apiRequests.getShots()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}