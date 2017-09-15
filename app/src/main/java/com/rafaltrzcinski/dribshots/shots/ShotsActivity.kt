package com.rafaltrzcinski.dribshots.shots

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rafaltrzcinski.dribshots.R
import com.rafaltrzcinski.dribshots.di.Injector

class ShotsActivity : AppCompatActivity(), ShotsActivityContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val presenter: ShotsActivityContract.Presenter = ShotsPresenter(Injector.component.getApiRequests())

        presenter.getShots()
    }
}
