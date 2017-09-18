package com.rafaltrzcinski.dribshots.shots

import com.rafaltrzcinski.dribshots.rest.model.Shot

interface ShotsActivityContract {

    interface View {
        fun loadShots(shots: List<Shot>)
        fun showLoadingError()
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun getShots()
    }
}