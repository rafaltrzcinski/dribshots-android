package com.rafaltrzcinski.dribshots.shots.list

import com.rafaltrzcinski.dribshots.rest.model.Shot

interface ShotsActivityContract {

    interface View {
        fun loadShots(shots: List<Shot>)
        fun showLoadingError()
        fun attachShotDetails(shot: Shot)
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun getShots()
        fun openShotDetails(shot: Shot)
    }
}