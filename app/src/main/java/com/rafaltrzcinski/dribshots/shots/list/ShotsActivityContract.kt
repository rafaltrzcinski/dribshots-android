package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.model.Shot
import okhttp3.Headers

interface ShotsActivityContract {

    interface View {
        fun loadShots(shots: List<Shot>)
        fun loadNextShots(shots: List<Shot>)
        fun showLoadingError()
        fun attachShotDetails(shot: Shot)
        fun startLoading()
        fun finishLoading()
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun getShots()
        fun getNextShots()
        fun openShotDetails(shot: Shot)
        fun getNextUrlFromLinkHeader(linkHeader: String?): String
        fun setCurrentNextLink(headers: Headers?)
    }
}